//
//  SettingViewController.m
//  dementia
//
//

#import "SettingViewController.h"

#import "AppDelegate.h"

#import "LoginViewController.h"

@interface SettingViewController ()

@property (strong, nonatomic) AppDelegate *appDelegate;

@property (strong, nonatomic) NSUserDefaults *userDefaults;

@property (strong, nonatomic) NSArray *gpsTimeSelection;

@end

@implementation SettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    
    self.userDefaults = [NSUserDefaults standardUserDefaults];
    
    self.gpsTimeSelection = @[[NSString stringWithFormat:@"5%@", LocalizedString(@"SETTING_GPS_TIME_MIN")],
                              [NSString stringWithFormat:@"10%@", LocalizedString(@"SETTING_GPS_TIME_MIN")],
                              [NSString stringWithFormat:@"20%@", LocalizedString(@"SETTING_GPS_TIME_MIN")],
                              [NSString stringWithFormat:@"30%@", LocalizedString(@"SETTING_GPS_TIME_MIN")],
                              [NSString stringWithFormat:@"45%@", LocalizedString(@"SETTING_GPS_TIME_MIN")],
                              [NSString stringWithFormat:@"60%@", LocalizedString(@"SETTING_GPS_TIME_MIN")],
                              LocalizedString(@"BUTTON_CANCEL")];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    [self setUpData];
}

- (void)viewDidLayoutSubviews {
    //[self.scrollBackground setContentSize:CGSizeMake(self.scrollBackground.frame.size.width, 505)];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_SETTING")];
    
    [self.pageTitleLbl setText:LocalizedString(@"SETTING_NORMAL")];
    
    [self.modeMark setText:LocalizedString(@"MAIN_REGISTER_MODE")];
    [self.modeFullMark setText:LocalizedString(@"MAIN_REGISTER_MODE_FULL")];
    [self.modeGameMark setText:LocalizedString(@"MAIN_REGISTER_MODE_GAME")];
    [self.languageMark setText:LocalizedString(@"SETTING_LANGUAGE")];
    [self.languageZhMark setText:LocalizedString(@"SETTING_LANGUAGE_ZH")];
    [self.languageGbMark setText:LocalizedString(@"SETTING_LANGUAGE_GB")];
    [self.fontSizeMark setText:LocalizedString(@"SETTING_FONT_SIZE")];
    [self.fontSizeLargeMark setText:LocalizedString(@"SETTING_FONT_SIZE_LARGE")];
    [self.fontSizeSmallMark setText:LocalizedString(@"SETTING_FONT_SIZE_SMALL")];
    [self.soundMark setText:LocalizedString(@"SETTING_SOUND")];
    [self.gpsMark setText:LocalizedString(@"SETTING_GPS")];
    [self.gpsTimeMark setText:LocalizedString(@"SETTING_GPS_TIME")];
    [self.autoUpdateMark setText:LocalizedString(@"SETTING_AUTO_UPDATE")];
    
    [self.clearFamilyDataBtn setTitle:LocalizedString(@"SETTING_CLEAR_FAMILY_DATA") forState:UIControlStateNormal];
    [self.clearRankingDataBtn setTitle:LocalizedString(@"SETTING_CLEAR_RANKING_DATA") forState:UIControlStateNormal];
    [self.logoutBtn setTitle:LocalizedString(@"SETTING_LOGOUT") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    
    for (UIView *view in self.scrollBackground.subviews) {
        if ([view isKindOfClass:[UILabel class]]) {
            [((UILabel *)view) setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
        }
        else if ([view isKindOfClass:[UIButton class]]) {
            [((UIButton *)view).titleLabel setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
        }
    }
}

- (void)setUpData {
    [self.modeBtn[0] setAccessibilityLabel:@""];
    [self.modeBtn[0] setAccessibilityValue:@"完整模式"];
    [self.modeBtn[1] setAccessibilityLabel:@""];
    [self.modeBtn[1] setAccessibilityValue:@"遊戲模式"];
    if ([[self.userDefaults objectForKey:@"userSetting:gameMode"] isEqualToString:@"Full"]) {
        [self.modeBtn[0] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
        [self.modeBtn[0] setAccessibilityTraits:[self.modeBtn[0] accessibilityTraits]|UIAccessibilityTraitSelected];
    }
    else {
        [self.modeBtn[1] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
        [self.modeBtn[1] setAccessibilityTraits:[self.modeBtn[1] accessibilityTraits]|UIAccessibilityTraitSelected];
    }

    [self.languageBtn[0] setAccessibilityLabel:@""];
    [self.languageBtn[0] setAccessibilityValue:@"繁體中文"];
    [self.languageBtn[1] setAccessibilityLabel:@""];
    [self.languageBtn[1] setAccessibilityValue:@"簡體中文"];
    if ([[self.userDefaults objectForKey:@"userSetting:language"] isEqualToString:@"ZH"]) {
        [self.languageBtn[0] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
        [self.languageBtn[0] setAccessibilityTraits:[self.languageBtn[0] accessibilityTraits]|UIAccessibilityTraitSelected];
    }
    else {
        [self.languageBtn[1] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
        [self.languageBtn[1] setAccessibilityTraits:[self.languageBtn[1] accessibilityTraits]|UIAccessibilityTraitSelected];
    }

    [self.fontSizeBtn[0] setAccessibilityLabel:@""];
    [self.fontSizeBtn[0] setAccessibilityValue:@"大字體"];
    [self.fontSizeBtn[1] setAccessibilityLabel:@""];
    [self.fontSizeBtn[1] setAccessibilityValue:@"小字體"];
    if ([[self.userDefaults objectForKey:@"userSetting:fontSize"] isEqualToString:@"Large"]) {
        [self.fontSizeBtn[0] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
        [self.fontSizeBtn[0] setAccessibilityTraits:[self.fontSizeBtn[0] accessibilityTraits]|UIAccessibilityTraitSelected];
    }
    else {
        [self.fontSizeBtn[1] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
        [self.fontSizeBtn[1] setAccessibilityTraits:[self.fontSizeBtn[1] accessibilityTraits]|UIAccessibilityTraitSelected];
    }
    
    [self.soundBtn setImage:[UIImage imageNamed:([[self.userDefaults objectForKey:@"userSetting:sound"] boolValue]) ? @"Button_Switchon" : @"Button_Switchoff"] forState:UIControlStateNormal];
    [self.soundBtn setAccessibilityLabel:@""];
    if ([[self.userDefaults objectForKey:@"userSetting:sound"] boolValue]) {
        [self.soundBtn setAccessibilityValue:@"聲效開關 已開啓"];
    } else {
        [self.soundBtn setAccessibilityValue:@"聲效開關 已關閉"];
    }
    
    [self.gpsTimeBtn setTitle:[NSString stringWithFormat:@"%ld%@", (long)[[self.userDefaults objectForKey:@"userSetting:gpsTime"] integerValue], LocalizedString(@"SETTING_GPS_TIME_MIN")] forState:UIControlStateNormal];
    [self.gpsTimeBtn setAccessibilityLabel:@""];
    [self.gpsTimeBtn setAccessibilityValue:[@"衛星定位隔距" stringByAppendingString:self.gpsTimeBtn.titleLabel.text]];
    
    [self.autoUpdateBtn setImage:[UIImage imageNamed:([[self.userDefaults objectForKey:@"userSetting:autoUpdate"] boolValue]) ? @"Button_Switchon" : @"Button_Switchoff"] forState:UIControlStateNormal];
    [self.autoUpdateBtn setAccessibilityLabel:@""];
    if ([[self.userDefaults objectForKey:@"userSetting:autoUpdate"] boolValue]) {
        [self.autoUpdateBtn setAccessibilityValue:@"內容更新開關 已開啓"];
    } else {
        [self.autoUpdateBtn setAccessibilityValue:@"內容更新開關 已關閉"];
    }
}

#pragma mark - IBAction

- (IBAction)modeBtnClick:(id)sender {
    NSInteger modeSelected = -1;
    
    for (int i=0; i<[self.modeBtn count]; i++) {
        [self.modeBtn[i] setAccessibilityTraits:UIAccessibilityTraitButton|UIAccessibilityTraitAllowsDirectInteraction];
        if (sender == self.modeBtn[i]) {
            [self.modeBtn[i] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
            [self.modeBtn[i] setAccessibilityTraits:[self.modeBtn[i] accessibilityTraits]|UIAccessibilityTraitSelected];
            modeSelected = i;
        }
        else {
            [self.modeBtn[i] setImage:nil forState:UIControlStateNormal];
        }
    }
    
    NSString *mode = (modeSelected == 0) ? @"Full" : @"Game";
    [self.userDefaults setValue:mode forKey:@"userSetting:gameMode"];
    [[JDDataManager sharedInstance] setGameMode:mode];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"Notification_UpdateMode" object:self];
}

- (IBAction)languageBtnClick:(id)sender {
    NSInteger languageSelected = -1;
    
    for (int i=0; i<[self.languageBtn count]; i++) {
        [self.languageBtn[i] setAccessibilityTraits:UIAccessibilityTraitButton|UIAccessibilityTraitAllowsDirectInteraction];
        if (sender == self.languageBtn[i]) {
            [self.languageBtn[i] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
            [self.languageBtn[i] setAccessibilityTraits:[self.languageBtn[i] accessibilityTraits]|UIAccessibilityTraitSelected];
            languageSelected = i;
        }
        else {
            [self.languageBtn[i] setImage:nil forState:UIControlStateNormal];
        }
    }
    
    NSString *languageCode = (languageSelected == 0) ? @"ZH" : @"GB";
    [self.userDefaults setValue:languageCode forKey:@"userSetting:language"];
    [[HMLocalization sharedInstance] setLanguage:languageCode];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"Notification_UpdateLocalizationView" object:self];
}

- (IBAction)fontSizeBtnClick:(id)sender {
    NSInteger fontSizeSelected = -1;
    
    for (int i=0; i<[self.fontSizeBtn count]; i++) {
        [self.fontSizeBtn[i] setAccessibilityTraits:UIAccessibilityTraitButton|UIAccessibilityTraitAllowsDirectInteraction];
        if (sender == self.fontSizeBtn[i]) {
            [self.fontSizeBtn[i] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
            [self.fontSizeBtn[i] setAccessibilityTraits:[self.fontSizeBtn[i] accessibilityTraits]|UIAccessibilityTraitSelected];
            fontSizeSelected = i;
        }
        else {
            [self.fontSizeBtn[i] setImage:nil forState:UIControlStateNormal];
        }
    }
    
    NSString *fontSize = (fontSizeSelected == 0) ? @"Large" : @"Size";
    [self.userDefaults setValue:fontSize forKey:@"userSetting:fontSize"];
    [[JDDataManager sharedInstance] setFontSize:fontSize];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"Notification_UpdateFontSize" object:self];
}

- (IBAction)soundBtnClick:(id)sender {
    NSString *imageName;
    
    if ([[self.userDefaults objectForKey:@"userSetting:sound"] boolValue]) {
        imageName = @"Button_Switchoff";
        
        [self.userDefaults setBool:NO forKey:@"userSetting:sound"];
        [self.userDefaults synchronize];
        
        [self.soundBtn setAccessibilityValue:@"聲效開關 已關閉"];
        
        [self.appDelegate.audioPlayer pause];
    }
    else {
        imageName = @"Button_Switchon";
        
        [self.userDefaults setBool:YES forKey:@"userSetting:sound"];
        [self.userDefaults synchronize];
        
        [self.soundBtn setAccessibilityValue:@"聲效開關 已開啓"];
        
        [self.appDelegate.audioPlayer play];
    }
    
    [self.soundBtn setImage:[UIImage imageNamed:imageName] forState:UIControlStateNormal];
}

- (IBAction)gpsTimeBtnClick:(id)sender {
    ShowAlertView(LocalizedString(@"ALERT_SETTING_GPS_TIME"), @"", self, self.gpsTimeSelection, 1)
}

- (IBAction)autoUpdateBtnClick:(id)sender {
    NSString *imageName;
    
    if ([[self.userDefaults objectForKey:@"userSetting:autoUpdate"] boolValue]) {
        imageName = @"Button_Switchoff";
        
        [self.userDefaults setBool:NO forKey:@"userSetting:autoUpdate"];
        [self.userDefaults synchronize];
        
        [self.autoUpdateBtn setAccessibilityValue:@"內容更新開關 已關閉"];
    }
    else {
        imageName = @"Button_Switchon";
        
        [self.userDefaults setBool:YES forKey:@"userSetting:autoUpdate"];
        [self.userDefaults synchronize];
        
        [self.autoUpdateBtn setAccessibilityValue:@"內容更新開關 已開啓"];
    }
    
    [self.autoUpdateBtn setImage:[UIImage imageNamed:imageName] forState:UIControlStateNormal];
}

- (IBAction)showUserGuideBtnClick:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://www.jccpa.org.hk/tc/facts_on_dementia/app/index.php"]];
}

- (IBAction)showAboutAppBtnClick:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"AboutAppViewController"] animated:YES];
}

- (IBAction)clearFamilyDataBtnClick:(id)sender {
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    if ([[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"DELETE FROM `contact`"]]) {
        NSFileManager *fileManager = [NSFileManager defaultManager];
        [fileManager removeItemAtPath:[NSHomeDirectory() stringByAppendingPathComponent:@"Documents/Media/Contact"] error:nil];

        [self.view makeToast:LocalizedString(@"BUTTON_FINISH")];
    }
    
    [db close];
}

- (IBAction)clearRankingDataBtnClick:(id)sender {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    if ([[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"DELETE FROM `game_score`"]] &&
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"UPDATE `game_level` SET `level` = '1' WHERE `user_id` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]]]) {
        
        [self.view makeToast:LocalizedString(@"BUTTON_FINISH")];
    }
    
    [db close];
}

- (IBAction)accessibilityStatementBtnClick:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"AccessibilityStatementViewController"] animated:YES];
}

- (IBAction)logoutBtnClick:(id)sender {
    [self.userDefaults setValue:@{} forKey:@"userInfo"];
    
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[LoginViewController class]]) {
            [self.navigationController popToViewController:vc animated:YES];
            break;
        }
    }
}

- (IBAction)accessibilityStatementBtnClient:(id)sender {
}

#pragma mark - UIAlertView delegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if ([alertView tag] == 1) {
        NSInteger gpsTime = 0;
        
        switch (buttonIndex) {
            case 0:
                gpsTime = 5;
                break;
                
            case 1:
                gpsTime = 10;
                break;
                
            case 2:
                gpsTime = 20;
                break;
            
            case 3:
                gpsTime = 30;
                break;
                
            case 4:
                gpsTime = 45;
                break;
                
            case 5:
                gpsTime = 60;
                break;
                
            default:
                gpsTime = -1;
                break;
        }
        
        if (gpsTime > -1) {
            [self.userDefaults setInteger:gpsTime forKey:@"userSetting:gpsTime"];
            [self.userDefaults synchronize];
            
            [self.gpsTimeBtn setTitle:[NSString stringWithFormat:@"%ld%@", (long)gpsTime, LocalizedString(@"SETTING_GPS_TIME_MIN")] forState:UIControlStateNormal];
            [self.gpsTimeBtn setAccessibilityValue:[@"衛星定位隔距" stringByAppendingString:self.gpsTimeBtn.titleLabel.text]];
            
            [self.appDelegate setUpTimer];
        }
    }
}

@end
