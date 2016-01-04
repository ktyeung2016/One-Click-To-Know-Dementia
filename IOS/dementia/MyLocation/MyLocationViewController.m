//
//  MyLocationViewController.m
//  dementia
//
//

#import "MyLocationViewController.h"

#import "ContactFamilyViewController.h"
#import "ContactData.h"

@interface MyLocationViewController ()

@property (strong, nonatomic) GMSMapView *locationMapView;

@property (strong, nonatomic) CLLocationManager *locationManager;

@end

@implementation MyLocationViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    self.locationManager = [[CLLocationManager alloc] init];
    [self.locationManager setDelegate:self];
    [self.locationManager setDistanceFilter:kCLDistanceFilterNone];
    [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
    
    if ([self.locationManager respondsToSelector:@selector(requestAlwaysAuthorization)]) {
        [self.locationManager requestAlwaysAuthorization] ;
        [self.locationManager requestWhenInUseAuthorization];
    }
    
    [self.locationManager startUpdatingLocation];
    
    [self setUpDemoView];
    [self setUpMapView];
    [self sendGPSToServer];
    
    UITapGestureRecognizer *gestureRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(demoLayerViewTap)];
    [self.demoLayerView addGestureRecognizer:gestureRecognizer];
}

- (void)viewWillAppear:(BOOL)animated {
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_MY_LOCATION")];
    
    [self.reloadBtn setTitle:LocalizedString(@"BUTTON_UPDATE_LOCATION") forState:UIControlStateNormal];
    [self.contactBtn setTitle:LocalizedString(@"BUTTON_CONTACT_FAMILY") forState:UIControlStateNormal];
    
    if (self.isModeDemo) {
        [self.useMethodMark setText:LocalizedString(@"LOCATION_DEMO_USE_METHOD")];
        [self.demoLbl setText:LocalizedString(@"LOCATION_DEMO_MSG_1")];
    }
}

- (void)setUpTextFontSize {
    [self.reloadBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.contactBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    if (self.isModeDemo) {
        [self.useMethodMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        [self.pagesLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        [self.demoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    }
}

- (void)setUpDemoView {
    if (!self.isModeDemo) {
        [self.demoTopView setHidden:YES];
        [self.demoLayerView setHidden:YES];
        
        [self.demoTopViewHeightConstraint setConstant:0];
        
        [self.view layoutIfNeeded];
    } else {
        UIBezierPath *overlayPath = [UIBezierPath bezierPathWithRect:self.view.bounds];

        CGRect screenRect = [UIScreen mainScreen].bounds;
        CGRect contactBtnRect = self.contactBtn.bounds;
        contactBtnRect.origin.x = screenRect.size.width/2+10;
        contactBtnRect.origin.y = screenRect.size.height - contactBtnRect.size.height - 100;
        contactBtnRect.size.width+=10;
        contactBtnRect.size.height+=5;
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:contactBtnRect];
        [overlayPath appendPath:transparentPath];
        [overlayPath setUsesEvenOddFillRule:true];
        
        CAShapeLayer *dimLayer = [CAShapeLayer layer];
        dimLayer.path = overlayPath.CGPath;
        dimLayer.fillRule = kCAFillRuleEvenOdd;
        dimLayer.fillColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.7].CGColor;
        
        [self.demoLayerMask.layer addSublayer:dimLayer];
    }
}

- (void)setUpMapView {
    GMSCameraPosition *camera = [GMSCameraPosition cameraWithLatitude:self.locationManager.location.coordinate.latitude
                                                            longitude:self.locationManager.location.coordinate.longitude
                                                                 zoom:17];
    
    if (CGRectIsEmpty(self.locationMapView.frame)) {
        self.locationMapView = [GMSMapView mapWithFrame:CGRectMake(0,
                                                                   self.demoTopViewHeightConstraint.constant,
                                                                   [[UIScreen mainScreen] bounds].size.width,
                                                                   [[UIScreen mainScreen] bounds].size.height - 64 - self.demoTopViewHeightConstraint.constant - 50)
                                                 camera:camera];
        
        [self.locationMapView setMyLocationEnabled:YES];
        [self.view addSubview:self.locationMapView];
        
        if (self.isModeDemo) {
            [self.view bringSubviewToFront:self.demoLayerView];
        }
    }
    else {
        [self.locationMapView setCamera:camera];
    }
}

- (void)sendGPSToServer {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    if ([userDefaults objectForKey:@"userInfo"][@"id"] != nil &&
        [CLLocationManager locationServicesEnabled] &&
        [CLLocationManager authorizationStatus] == kCLAuthorizationStatusAuthorized) {
        
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"EEE MMM d HH:mm:ss ZZZ yyyy"];
        
        NSMutableString *family = [[NSMutableString alloc] init];
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [family appendString:@"["];
        bool addedFamily = FALSE;
        for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                        getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `contact` WHERE `create_user` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]]
                                        keys:ContactTableAttributes]) {
            
            ContactData *data = [[ContactData alloc] initWithData:tempDict];
            if (addedFamily) {
                [family appendString:@","];
            }
            [family appendFormat:@"{\"name\":\"%@\", \"email\":\"%@\"}", data.memberName, data.memberEmail];
            addedFamily = TRUE;
        }
        [family appendString:@"]"];
        
        [db close];
        
        NSDictionary *tempDict = @{@"position": [NSString stringWithFormat:@"%.8f:%.8f:%@",
                                                 self.locationManager.location.coordinate.longitude,
                                                 self.locationManager.location.coordinate.latitude,
                                                 [dateFormatter stringFromDate:[NSDate date]]],
                                   @"user_id": [userDefaults objectForKey:@"userInfo"][@"id"],
                                   @"family": family};
        
        [[JDDataManager sharedInstance] getJSONWithAPI:@"send_position" parameter:tempDict result:^(NSDictionary *result) {
            
        } errorMsg:^(NSString *errorMsg) {
            
        }];
    }
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    [self.navigationController popToViewController:self.navigationController.viewControllers[2] animated:YES];
}

- (IBAction)nextBtnClick:(id)sender {
    [self demoLayerViewTap];
}

- (IBAction)reloadBtnClick:(id)sender {
    [self setUpMapView];
}

- (IBAction)contactBtnClick:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"ContactFamilyViewController"] animated:YES];
}

#pragma mark - UITapGestureRecognizer handling

- (void)demoLayerViewTap {
    ContactFamilyViewController *contactFamilyVC = [self.storyboard instantiateViewControllerWithIdentifier:@"ContactFamilyViewController"];
    [contactFamilyVC setIsModeDemo:YES];
    [contactFamilyVC setCurrentPageNumber:2];
    
    [self.navigationController pushViewController:contactFamilyVC animated:NO];
}

@end
