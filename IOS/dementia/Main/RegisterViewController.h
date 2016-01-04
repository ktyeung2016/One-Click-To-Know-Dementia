//
//  RegisterViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface RegisterViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *registerMark;
@property (weak, nonatomic) IBOutlet UILabel *accountMark;
@property (weak, nonatomic) IBOutlet UILabel *ageMark;
@property (weak, nonatomic) IBOutlet UILabel *genderMark;
@property (weak, nonatomic) IBOutlet UILabel *genderMaleMark;
@property (weak, nonatomic) IBOutlet UILabel *genderFemaleMark;
@property (weak, nonatomic) IBOutlet UILabel *modeMark;
@property (weak, nonatomic) IBOutlet UILabel *modeGameMark;
@property (weak, nonatomic) IBOutlet UILabel *modeFullMark;
@property (weak, nonatomic) IBOutlet UIButton *resetBtn;
@property (weak, nonatomic) IBOutlet UIButton *submitBtn;
@property (weak, nonatomic) IBOutlet UIButton *backBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UITextField *accountTxtField;
@property (weak, nonatomic) IBOutlet UITextField *ageTxtField;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *genderBtn;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *modeBtn;

- (IBAction)genderBtnClick:(id)sender;
- (IBAction)modeBtnClick:(id)sender;
- (IBAction)resetBtnClick:(id)sender;
- (IBAction)submitBtnClick:(id)sender;
- (IBAction)backBtnClick:(id)sender;

@end
