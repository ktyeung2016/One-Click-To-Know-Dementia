//
//  RegisterViewController.m
//  dementia
//
//

#import "RegisterViewController.h"

#import "UIView+UIComponent.h"

@interface RegisterViewController ()

@property (assign, nonatomic) NSInteger genderSelected;
@property (assign, nonatomic) NSInteger modeSelected;

@end

@implementation RegisterViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.genderSelected = 0;
    self.modeSelected = 1;
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self.accountTxtField setTextFieldEdge];
    [self.ageTxtField setTextFieldEdge];
}

- (void)setUpLocalizationView {
    [self.registerMark setText:LocalizedString(@"BUTTON_REGISTER")];
    [self.accountMark setText:LocalizedString(@"MAIN_LOGIN_ACCOUNT")];
    [self.ageMark setText:LocalizedString(@"MAIN_REGISTER_AGE")];
    [self.genderMark setText:LocalizedString(@"MAIN_REGISTER_GENDER")];
    [self.genderMaleMark setText:LocalizedString(@"MAIN_REGISTER_GENDER_MALE")];
    [self.genderFemaleMark setText:LocalizedString(@"MAIN_REGISTER_GENDER_FEMALE")];
    [self.modeMark setText:LocalizedString(@"MAIN_REGISTER_MODE")];
    [self.modeGameMark setText:LocalizedString(@"MAIN_REGISTER_MODE_GAME")];
    [self.modeFullMark setText:LocalizedString(@"MAIN_REGISTER_MODE_FULL")];
    
    [self.resetBtn setTitle:LocalizedString(@"BUTTON_RESET") forState:UIControlStateNormal];
    [self.submitBtn setTitle:LocalizedString(@"BUTTON_SUBMIT") forState:UIControlStateNormal];
    [self.backBtn setTitle:LocalizedString(@"BUTTON_BACK") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.registerMark setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    [self.accountMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.ageMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.genderMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.genderMaleMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.genderFemaleMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.modeMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.modeGameMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.modeFullMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.resetBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.submitBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.backBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.accountTxtField setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.ageTxtField setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
}

#pragma mark - IBAction

- (IBAction)genderBtnClick:(id)sender {
    for (int i=0; i<[self.genderBtn count]; i++) {
        if (sender == self.genderBtn[i]) {
            [self.genderBtn[i] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
            self.genderSelected = i;
        }
        else {
            [self.genderBtn[i] setImage:nil forState:UIControlStateNormal];
        }
    }
}

- (IBAction)modeBtnClick:(id)sender {
    for (int i=0; i<[self.modeBtn count]; i++) {
        if (sender == self.modeBtn[i]) {
            [self.modeBtn[i] setImage:[UIImage imageNamed:@"Checkbox_Tick_Orange"] forState:UIControlStateNormal];
            self.modeSelected = i;
        }
        else {
            [self.modeBtn[i] setImage:nil forState:UIControlStateNormal];
        }
    }
}

- (IBAction)resetBtnClick:(id)sender {
    [self.accountTxtField setText:@""];
    [self.ageTxtField setText:@""];
    
    for (int i=0; i<[self.genderBtn count]; i++) {
        [self.genderBtn[i] setImage:nil forState:UIControlStateNormal];
        [self.modeBtn[i] setImage:nil forState:UIControlStateNormal];
    }
    
    self.genderSelected = -1;
    self.modeSelected = -1;
}

- (IBAction)submitBtnClick:(id)sender {
    [self.view  endEditing:YES];
    
    if ([self.accountTxtField.text isEqualToString:@""] || [self.ageTxtField.text isEqualToString:@""] || self.genderSelected == -1 || self.modeSelected == -1) {
        NSString *message;
        
        if ([self.accountTxtField.text isEqualToString:@""]) {
            message = [NSString stringWithFormat:@"%@%@", LocalizedString(@"ALERT_PLEASE_ENTER"), LocalizedString(@"MAIN_LOGIN_ACCOUNT")];
        }
        else if ([self.ageTxtField.text isEqualToString:@""]) {
            message = [NSString stringWithFormat:@"%@%@", LocalizedString(@"ALERT_PLEASE_ENTER"), LocalizedString(@"MAIN_REGISTER_AGE")];
        }
        
        [self.view makeToast:message];
    }
    else {
        ShowToastActivity;
        
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        NSString *userKey = [userDefaults valueForKey:@"user_key"];
        if (userKey==nil) {
            userKey = @"";
        }
        NSDictionary *parameter = @{@"nickname": [self.accountTxtField.text stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
                                    @"login": self.accountTxtField.text,
                                    @"age": self.ageTxtField.text,
                                    @"gender": [NSString stringWithFormat:@"%lu", (long)self.genderSelected],
                                    @"type": [NSString stringWithFormat:@"%d", (self.modeSelected == 0) ? 1 : 0],
                                    @"device_type": @"iPhone",
                                    @"user_key": userKey};
        
        [[JDDataManager sharedInstance] getJSONWithAPI:@"register" parameter:parameter result:^(NSDictionary *result) {
            HideToastActivity;
            
            if (result != nil) {
                if ([result[@"status"] isEqualToString:@"Y"]) {
                    if (userKey==nil || [userKey isEqual:@""]) {
                        [userDefaults setObject:result[@"user"][0][@"user_key"] forKey:@"user_key"];
                    }
                    [userDefaults synchronize];
                    
                    [self.navigationController popViewControllerAnimated:YES];
                    [self.parentViewController.view makeToast:LocalizedString(@"ALERT_REGISTER_SUCCESS")];
                }
                else {
                    if ([result[@"msg"] isEqualToString:@"USER_DUPLICATED_LOGIN"]) {                        
                        [self.view makeToast:LocalizedString(@"ALERT_REGISTER_USER_DUPLICATED_LOGIN")];
                    }
                }
            }
        } errorMsg:^(NSString *errorMsg) {
            HideToastActivity;
        }];
    }
}

- (IBAction)backBtnClick:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - Keyboard handling

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    UITouch *touch = [[event allTouches] anyObject];
    
    if ([self.accountTxtField isFirstResponder] && [touch view] != self.accountTxtField) {
        [self.accountTxtField resignFirstResponder];
    }
    else if ([self.ageTxtField isFirstResponder] && [touch view] != self.ageTxtField) {
        [self.ageTxtField resignFirstResponder];
    }
    
    [super touchesBegan:touches withEvent:event];
}

@end
