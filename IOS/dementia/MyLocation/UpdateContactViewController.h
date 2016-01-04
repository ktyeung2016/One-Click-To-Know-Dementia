//
//  UpdateContactViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface UpdateContactViewController : BaseViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate, UITextFieldDelegate, UITextViewDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UIButton *takePhotoBtn;
@property (weak, nonatomic) IBOutlet UIButton *choosePhotoBtn;
@property (weak, nonatomic) IBOutlet UILabel *nameMark;
@property (weak, nonatomic) IBOutlet UILabel *relationshipMark;
@property (weak, nonatomic) IBOutlet UILabel *phoneMark;
@property (weak, nonatomic) IBOutlet UILabel *emailMark;
@property (weak, nonatomic) IBOutlet UILabel *remarkMark;
@property (weak, nonatomic) IBOutlet UIButton *cancelBtn;
@property (weak, nonatomic) IBOutlet UIButton *finishBtn;

@property (weak, nonatomic) IBOutlet UILabel *useMethodMark;

/* Variable */
@property (weak, nonatomic) IBOutlet UIImageView *profileImgView;
@property (weak, nonatomic) IBOutlet UITextField *nameTxtField;
@property (weak, nonatomic) IBOutlet UITextField *relationshipTxtField;
@property (weak, nonatomic) IBOutlet UITextField *phoneTxtField;
@property (weak, nonatomic) IBOutlet UITextField *emailTxtField;
@property (weak, nonatomic) IBOutlet UITextView *remarkTxtView;

@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;

@property (weak, nonatomic) IBOutlet UIView *demoTopView;
@property (weak, nonatomic) IBOutlet UIView *demoLayerView;
@property (weak, nonatomic) IBOutlet UIView *demoLayerMask;

@property (weak, nonatomic) IBOutlet UIView *middleDemoView;
@property (weak, nonatomic) IBOutlet UILabel *middleDemoLbl;

@property (weak, nonatomic) IBOutlet UIView *bottomDemoView;
@property (weak, nonatomic) IBOutlet UILabel *bottomDemoLbl;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *demoTopViewHeightConstraint;

@property (copy, nonatomic) void(^contactUpdateBlock)(BOOL status);

@property (strong, nonatomic) NSDictionary *editContactDict;

@property (assign, nonatomic) BOOL isModeDemo;

- (IBAction)backBtnClick:(id)sender;
- (IBAction)nextBtnClick:(id)sender;

- (IBAction)takePhotoBtnClick:(id)sender;
- (IBAction)choosePhotoBtnClick:(id)sender;
- (IBAction)cancelBtnClick:(id)sender;
- (IBAction)finishBtnClick:(id)sender;

@end
