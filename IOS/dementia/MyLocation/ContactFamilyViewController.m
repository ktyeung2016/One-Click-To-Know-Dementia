//
//  ContactFamilyViewController.m
//  dementia
//
//

#import "ContactFamilyViewController.h"

#import "ContactCell.h"
#import "ContactData.h"

#import "UpdateContactViewController.h"

@interface ContactFamilyViewController ()

@property (strong, nonatomic) NSMutableArray *itemList;
@property (strong, nonatomic) NSMutableArray *itemDictList;

@end

@implementation ContactFamilyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self setUpDemoView];
    [self setUpData];
    
    UITapGestureRecognizer *gestureRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(demoLayerViewTap)];
    [self.demoLayerView addGestureRecognizer:gestureRecognizer];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"BUTTON_CONTACT_FAMILY")];
    
    [self.addMemberBtn setTitle:LocalizedString(@"BUTTON_ADD_FAMILY_MEMBER") forState:UIControlStateNormal];
    [self.emergencyHotlineMark setText:LocalizedString(@"MY_LOCATION_EMERGENCY_HOTLINE")];
    
    if (self.isModeDemo) {
        [self.useMethodMark setText:LocalizedString(@"LOCATION_DEMO_USE_METHOD")];
        
        NSString *localizationKey = [NSString stringWithFormat:@"LOCATION_DEMO_MSG_%lu", (unsigned long)self.currentPageNumber];
        [self.demoLbl setText:LocalizedString(localizationKey)];
    }    
}

- (void)setUpTextFontSize {
    [self.addMemberBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    [self.emergencyHotlineMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.emergencyHotlineLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
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
    }
    else {
        [self.pagesLbl setText:[NSString stringWithFormat:@"%lu/10", (unsigned long)self.currentPageNumber]];
        
        UIBezierPath *overlayPath = [UIBezierPath bezierPathWithRect:self.view.bounds];
        
        CGRect screenRect = [UIScreen mainScreen].bounds;
        CGRect addMemberBtnRect = self.addMemberBtn.bounds;
        addMemberBtnRect.origin.x = screenRect.size.width-addMemberBtnRect.size.width-20;
        addMemberBtnRect.origin.y = 5;
        addMemberBtnRect.size.width+=10;
        addMemberBtnRect.size.height+=5;
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:addMemberBtnRect];
        [overlayPath appendPath:transparentPath];
        [overlayPath setUsesEvenOddFillRule:true];
        
        CAShapeLayer *dimLayer = [CAShapeLayer layer];
        dimLayer.path = overlayPath.CGPath;
        dimLayer.fillRule = kCAFillRuleEvenOdd;
        dimLayer.fillColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.7].CGColor;
        
        [self.demoLayerMask.layer addSublayer:dimLayer];
    }
}

- (void)setUpData {
    self.itemList = [NSMutableArray array];
    self.itemDictList = [NSMutableArray array];
    
    if (self.isModeDemo && self.currentPageNumber == 10) {
        for (int i=0; i<5; i++) {
            ContactData *data = [[ContactData alloc] initWithData:@{@"name": @"Man Chan",
                                                                    @"relationship": @"兒子",
                                                                    @"phone": @"12345678",
                                                                    @"file_media": @""}];
            [self.itemList addObject:data];
        }
        
        for (CALayer *layer in self.demoLayerMask.layer.sublayers) {
            [layer removeFromSuperlayer];
        }
    }
    else {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                        getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `contact` WHERE `create_user` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]]
                                        keys:ContactTableAttributes]) {
            
            ContactData *data = [[ContactData alloc] initWithData:tempDict];
            [self.itemList addObject:data];
            [self.itemDictList addObject:tempDict];
        }
        
        [db close];
    }
    
    [self.contactTable reloadData];
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    if (self.isModeDemo) {
        [self.navigationController popToViewController:self.navigationController.viewControllers[2] animated:YES];
    }
    else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (IBAction)backBtnClick:(id)sender {
    [self.navigationController popViewControllerAnimated:NO];
}

- (IBAction)nextBtnClick:(id)sender {
    [self demoLayerViewTap];
}

- (IBAction)addMemberBtnClick:(id)sender {
    UpdateContactViewController *updateContactVC = [self.storyboard instantiateViewControllerWithIdentifier:@"UpdateContactViewController"];
    
    updateContactVC.contactUpdateBlock = ^(BOOL status) {
        if (status) {
            [self setUpData];
        }
    };
    
    [self.navigationController pushViewController:updateContactVC animated:YES];
}

- (IBAction)editMemberBtnClick:(id)sender {
    UpdateContactViewController *updateContactVC = [self.storyboard instantiateViewControllerWithIdentifier:@"UpdateContactViewController"];
    
    [updateContactVC setEditContactDict:self.itemDictList[[sender tag]]];
    
    [self.navigationController pushViewController:updateContactVC animated:YES];
    
    updateContactVC.contactUpdateBlock = ^(BOOL status) {
        if (status) {
            [self setUpData];
        }
    };
}

- (IBAction)callMemberBtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", ((UIButton *)sender).titleLabel.text]]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", ((UIButton *)sender).titleLabel.text]]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)callHotlineBtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:26366323"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:26366323"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)call999BtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:999"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:999"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)call23432255BtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:23432255"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:23432255"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)call23388312BtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:23388312"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:23388312"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)call23820881BtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:23820881"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:23820881"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)call27876865BtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:27876865"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:27876865"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)call18288BtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:18288"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:18288"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}
#pragma mark - UITableView datasource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.itemList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ContactCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ContactCell" forIndexPath:indexPath];
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
    
    [cell.editMemberBtn setTag:indexPath.row];
    [cell.callMemberBtn setTag:indexPath.row];
    
    [cell setUpViewWithData:self.itemList[indexPath.row]];
    
    return cell;
}

#pragma mark - UITapGestureRecognizer handling

- (void)demoLayerViewTap {
    if (self.currentPageNumber == 2) {
        UpdateContactViewController *updateContactVC = [self.storyboard instantiateViewControllerWithIdentifier:@"UpdateContactViewController"];
        [updateContactVC setIsModeDemo:YES];
        
        [self.navigationController pushViewController:updateContactVC animated:NO];
    }
    else {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
         [db executeUpdate:@"UPDATE `location_demo` SET `shown` = '1' WHERE `user_id` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]]];
        
        [db close];
        
        [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"MyLocationViewController"] animated:YES];
    }
}

@end
