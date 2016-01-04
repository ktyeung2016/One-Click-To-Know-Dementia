//
//  TermsViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface TermsViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UITextView *termsTxtView;
@property (weak, nonatomic) IBOutlet UIView *popUpView;

- (IBAction)crossBtnClick:(id)sender;
- (IBAction)confirmBtnClick:(id)sender;
- (IBAction)notConfirmBtnClick:(id)sender;

- (IBAction)closeBtnClick:(id)sender;

@end
