//
//  SelfAssignmentViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface SelfAssignmentViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UILabel *questionNumberMark1;
@property (weak, nonatomic) IBOutlet UILabel *questionNumberMark2;
@property (weak, nonatomic) IBOutlet UILabel *remarkLbl;
@property (weak, nonatomic) IBOutlet UIButton *yesBtn;
@property (weak, nonatomic) IBOutlet UIButton *noBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (weak, nonatomic) IBOutlet UITextView *contentTxtView;

- (IBAction)answerBtnClick:(id)sender;

@end
