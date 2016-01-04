//
//  LoginViewController.m
//  dementia
//
//

#import "LoginViewController.h"

#import "TermsViewController.h"

#import "UIView+UIComponent.h"

@interface LoginViewController ()

@property (strong, nonatomic) NSUserDefaults *userDefaults;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(setUpLocalizationView)
                                                 name:@"Notification_UpdateLocalizationView"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(setUpTextFontSize)
                                                 name:@"Notification_UpdateFontSize"
                                               object:nil];
    
    self.userDefaults = [NSUserDefaults standardUserDefaults];
    
    if ([[self.userDefaults objectForKey:@"termsShown"]  isEqual:@0]) {
        TermsViewController *termsVC = [self.storyboard instantiateViewControllerWithIdentifier:@"TermsViewController"];
        [termsVC setModalPresentationStyle:(IsIOS8) ? UIModalPresentationCustom : UIModalPresentationFormSheet];
        
        [self.navigationController presentViewController:termsVC animated:YES completion:nil];
    }

    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    [self.accountTxtField setTextFieldEdge];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self.navigationController setNavigationBarHidden:YES];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [self checkData];
}

- (void)setUpLocalizationView {
    [self.accountMark setText:LocalizedString(@"MAIN_LOGIN_ACCOUNT")];
    [self.loginBtn setTitle:LocalizedString(@"BUTTON_LOGIN") forState:UIControlStateNormal];
    [self.registerBtn setTitle:LocalizedString(@"BUTTON_REGISTER") forState:UIControlStateNormal];
    [self.notRegisterBtn setTitle:LocalizedString(@"BUTTON_NOT_REGISTER") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.accountMark setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    [self.loginBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.registerBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.notRegisterBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.accountTxtField setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
}

- (void)checkData {
    if ([self.userDefaults objectForKey:@"userInfo"] != nil && ![[self.userDefaults objectForKey:@"userInfo"] isEqual:@{}]) {
        [self.accountTxtField setText:[self.userDefaults objectForKey:@"userInfo"][@"login"]];
        [self loginWithLoginName:[self.userDefaults objectForKey:@"userInfo"][@"login"]];
    }
}

- (void)insertPreloadDataIntoDatabaseWithUserId:(NSString *)userId {
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSArray *gameLevelArray = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ?", userId] keys:GameLevelTableAttributes];
    
    if ([gameLevelArray count] == 0) {
        for (int i=1; i<6; i++) {
            [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"INSERT INTO `game_level` (`game_type`,  `user_id`) VALUES (?, ?)", [NSString stringWithFormat:@"%d", i], userId]];
            [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"INSERT INTO `game_demo` (`game_type`, `user_id`) VALUES (?, ?)", [NSString stringWithFormat:@"%d", i], userId]];
        }
        
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"INSERT INTO `location_demo` (`user_id`) VALUES (?)", userId]];
    }
    
    [db close];
}

#pragma mark - IBAction

- (IBAction)loginBtnClick:(id)sender {
    [self.view endEditing:YES];
    
    if ([self.accountTxtField.text isEqualToString:@""]) {
        [self.view makeToast:[NSString stringWithFormat:@"%@%@", LocalizedString(@"ALERT_PLEASE_ENTER"), LocalizedString(@"MAIN_LOGIN_ACCOUNT")]];
    }
    else {
        [self loginWithLoginName:[self.accountTxtField.text stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
    }
}

- (IBAction)registerBtnClick:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"RegisterViewController"] animated:YES];
}

- (IBAction)notRegisterBtnClick:(id)sender {
    [self insertPreloadDataIntoDatabaseWithUserId:@"0"];
    
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"HomeViewController"] animated:YES];
}

#pragma mark - Keyboard handling

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    UITouch *touch = [[event allTouches] anyObject];
    
    if ([self.accountTxtField isFirstResponder] && [touch view] != self.accountTxtField) {
        [self.accountTxtField resignFirstResponder];
    }
    
    [super touchesBegan:touches withEvent:event];
}

#pragma mark - Connect API

- (void)loginWithLoginName:(NSString *)loginName {
    ShowToastActivity;

    NSString *userKey = [self.userDefaults valueForKey:@"user_key"];
    if (userKey==nil) {
        userKey = @"";
    }
    [[JDDataManager sharedInstance] getJSONWithAPI:@"login" parameter:@{@"login": loginName, @"user_key": userKey} result:^(NSDictionary *result) {
        HideToastActivity;
        
        if (result != nil) {
            if ([result[@"status"] isEqualToString:@"Y"]) {

                [self.userDefaults setObject:result[@"user"][0] forKey:@"userInfo"];
                if (userKey==nil || [userKey isEqual:@""]) {
                    [self.userDefaults setObject:result[@"user"][0][@"user_key"] forKey:@"user_key"];
                }
                [self.userDefaults synchronize];
                
                [self insertPreloadDataIntoDatabaseWithUserId:result[@"user"][0][@"id"]];
                
                [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"HomeViewController"] animated:YES];
            }
            else {
                [self.view makeToast:LocalizedString(@"ALERT_LOGIN_FAIL")];
            }
        }
    } errorMsg:^(NSString *errorMsg) {
        HideToastActivity;
        
        [self.view makeToast:LocalizedString(@"ALERT_NO_NETWORK")];
    }];
}

@end
