//
//  SelfAssignmentResultViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface SelfAssignmentResultViewController : BaseViewController <UIWebViewDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UILabel *resultMark;
@property (weak, nonatomic) IBOutlet UIButton *callBtn;
@property (weak, nonatomic) IBOutlet UIButton *retryBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UIScrollView *scrollResult;

@property (strong, nonatomic) NSMutableArray *yesQuestionArray;

@property (copy, nonatomic) void(^retryBlock)(BOOL status);

- (IBAction)callBtnClick:(id)sender;
- (IBAction)retryBtnClick:(id)sender;

@end
