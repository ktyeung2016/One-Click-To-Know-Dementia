//
//  HomeViewController.m
//  dementia
//
//

#import "HomeViewController.h"

#import "MyLocationViewController.h"

#import "NewsViewController.h"

@interface HomeViewController ()

@property (assign, nonatomic) BOOL isNewsShown;
@property (nonatomic) NSString* directFamilyContact;

@end

@implementation HomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(setUpCategoryButtonView)
                                                 name:@"Notification_UpdateMode"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(setUpLocalizationView)
                                                 name:@"Notification_UpdateLocalizationView"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(setUpTextFontSize)
                                                 name:@"Notification_UpdateFontSize"
                                               object:nil];
    
    [self setUpCategoryButtonView];
    [self setUpLocalizationView];
    [self setUpTextFontSize];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    [self setUpDirectFamilyContact];

    [self.navigationController setNavigationBarHidden:YES];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [self setUpBannerImageView];
    
    if (!self.isNewsShown) {
        [self showNewsView];
    }
}

- (void)setUpCategoryButtonView {
    if ([[[JDDataManager sharedInstance] returnGameMode] isEqualToString:@"Full"]) {
        [self.gameView setHidden:YES];
        [self.fullView setHidden:NO];
    }
    else {
        [self.gameView setHidden:NO];
        [self.fullView setHidden:YES];
    }
}

- (void)setUpLocalizationView {
    NSArray *fullLocalizationKeys = @[@"KNOWLEDGE", @"RESOURCE", @"SELF_ASSIGNMENT", @"GAME", @"MY_LOCATION", @"SETTING"];
    NSArray *gameLocalizationKeys = @[@"GAME", @"MY_LOCATION", @"CALL"];
    
    for (int i=0; i<[self.fullCategoryNameLbl count]; i++) {
        NSString *localizationKey = [NSString stringWithFormat:@"MAIN_CATEGORY_%@", fullLocalizationKeys[i]];
        [self.fullCategoryNameLbl[i] setText:LocalizedString(localizationKey)];
    }
    
    for (int i=0; i<[self.gameCategoryNameLbl count]; i++) {
        NSString *localizationKey = [NSString stringWithFormat:@"MAIN_CATEGORY_%@", gameLocalizationKeys[i]];
        [self.gameCategoryNameLbl[i] setText:LocalizedString(localizationKey)];
    }
}

- (void)setUpTextFontSize {
    for (UILabel *tempLbl in self.fullCategoryNameLbl) {
        [tempLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    }
    
    for (UILabel *tempLbl in self.gameCategoryNameLbl) {
        [tempLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    }
}

- (void)setUpDirectFamilyContact {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    [self.btnDirectFamilyContact setHidden:true];
    [self.lblDirectFamilyContact setHidden:true];
    for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `contact` WHERE `create_user` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]]
                                    keys:ContactTableAttributes]) {

        NSString *documentsPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
        NSString *folderPath = [documentsPath stringByAppendingPathComponent:@"Media/Contact"];
        NSString *filePath = [folderPath stringByAppendingPathComponent:tempDict[@"file_media"]];

        self.btnDirectFamilyContact.layer.cornerRadius = 5;
        self.btnDirectFamilyContact.clipsToBounds = true;
        [[self.btnDirectFamilyContact imageView] setContentMode:UIViewContentModeScaleAspectFit];
        [self.btnDirectFamilyContact setImage:[UIImage imageWithContentsOfFile:filePath] forState:UIControlStateNormal];
        [self.lblDirectFamilyContact setText:tempDict[@"name"]];
        [self.btnDirectFamilyContact setAccessibilityLabel:[NSString stringWithFormat:@"聯絡%@", tempDict[@"name"]]];
        self.directFamilyContact = tempDict[@"phone"];
        [self.btnDirectFamilyContact setHidden:false];
        [self.lblDirectFamilyContact setHidden:false];
        break;
    }
    
    [db close];
}

- (void)showNewsView {
    self.isNewsShown = true;
    
    NSString *contentString = @"";
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `news`"]
                                    keys:NewsTableAttributes]) {
        contentString = [[contentString stringByAppendingString:tempDict[[NSString stringWithFormat:@"html_content_%@", LanguageForDataAttribute]]] stringByAppendingString:@"</br>"];
    }
    
    [db close];
    
    if (![contentString isEqualToString:@""]) {
        NewsViewController *newsVC = [self.storyboard instantiateViewControllerWithIdentifier:@"NewsViewController"];
        [newsVC setModalPresentationStyle:(IsIOS8) ? UIModalPresentationCustom : UIModalPresentationFormSheet];
        
        [newsVC setContentString:contentString];
        
        [self.navigationController presentViewController:newsVC animated:YES completion:nil];
    }
}

- (void)setUpBannerImageView {
    [[AFNetworkReachabilityManager sharedManager] startMonitoring];
    [[AFNetworkReachabilityManager sharedManager] setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
        if (status == AFNetworkReachabilityStatusReachableViaWiFi || status == AFNetworkReachabilityStatusReachableViaWWAN) {
            [[JDDataManager sharedInstance] getJSONWithAPI:@"get_adbanner" parameter:nil result:^(NSDictionary *result) {
                if (result) {                    
                    for (NSDictionary *tempDict in result[@"media"]) {
                        [[JDDataManager sharedInstance] getMediaWithMediaName:tempDict[@"file_media"] result:^(UIImage *result) {
                            [self.bannerImgView setImage:result];
                        } errorMsg:^(NSString *errorMsg) {
                            
                        }];
                    }
                }
            } errorMsg:^(NSString *errorMsg) {
                
            }];
        }
    }];
    [[AFNetworkReachabilityManager sharedManager] stopMonitoring];
}

- (void)pushControllerWithStoryboardName:(NSString *)storyboardName identifier:(NSString *)identifier animated:(BOOL)animated {
    BOOL canPush = true;
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    if ([identifier isEqualToString:@"MyLocationViewController"]) {
        if ([userDefaults objectForKey:@"userInfo"] == nil || [[userDefaults objectForKey:@"userInfo"] isEqual:@{}]) {
            ShowAlertViewNormal(@"", LocalizedString(@"ALERT_LOGIN_TO_USE"), self, LocalizedString(@"BUTTON_LOGIN"), nil, 1)
            
            canPush = false;
        }
    }
    
    if (canPush) {
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        NSDictionary *tempDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `location_demo` WHERE `user_id` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:LocationDemoTableAttributes].firstObject;
        
        [db close];
        
        UIStoryboard *storyboard = [UIStoryboard storyboardWithName:storyboardName bundle:nil];
        BOOL debugDemoView = false;
        if ((debugDemoView || [tempDict[@"shown"] isEqualToString:@"0"]) && [identifier isEqualToString:@"MyLocationViewController"]) {
            MyLocationViewController *locationVC = [storyboard instantiateViewControllerWithIdentifier:identifier];
            [locationVC setIsModeDemo:YES];
            
            [self.navigationController pushViewController:locationVC animated:YES];
        }
        else {
            [self.navigationController pushViewController:[storyboard instantiateViewControllerWithIdentifier:identifier] animated:animated];
        }
        
        [self.navigationController setNavigationBarHidden:NO];
    }
}

#pragma mark - IBAction

- (IBAction)categoryBtnClick:(id)sender {
    NSString *storyboardName;
    NSString *identifier;
    
    switch ([sender tag]) {
        case 1:
            storyboardName = @"Knowledge";
            identifier = @"KnowledgeListViewController";
            break;
            
        case 2:
            storyboardName = @"Resource";
            identifier = @"ResourceListViewController";
            break;
            
        case 3:
            storyboardName = @"SelfAssignment";
            identifier = @"SelfAssignmentViewController";
            break;
            
        case 4:
        case 7:
            storyboardName = @"Game";
            identifier = @"GameListViewController";
            break;
            
        case 5:
        case 8:
            storyboardName = @"MyLocation";
            identifier = @"MyLocationViewController";
            break;

        case 6:
            storyboardName = @"Setting";
            identifier = @"SettingViewController";
            break;
            
        case 9:
            storyboardName = @"MyLocation";
            identifier = @"ContactFamilyViewController";
            break;
            
        case 10:
            storyboardName = @"MyLocation";
            identifier = @"MyLocationViewController";
            if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
                [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", self.directFamilyContact]]]) {
                
                [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", self.directFamilyContact]]];
            }
            break;
    }

    [self pushControllerWithStoryboardName:storyboardName identifier:identifier animated:YES];
}

#pragma mark - UIAlertView delegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if ([alertView tag] == 1) {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

@end
