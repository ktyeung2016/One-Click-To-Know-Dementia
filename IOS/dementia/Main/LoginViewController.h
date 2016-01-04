//
//  LoginViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface LoginViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *accountMark;
@property (weak, nonatomic) IBOutlet UIButton *loginBtn;
@property (weak, nonatomic) IBOutlet UIButton *registerBtn;
@property (weak, nonatomic) IBOutlet UIButton *notRegisterBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UITextField *accountTxtField;

- (IBAction)loginBtnClick:(id)sender;
- (IBAction)registerBtnClick:(id)sender;
- (IBAction)notRegisterBtnClick:(id)sender;

@end
