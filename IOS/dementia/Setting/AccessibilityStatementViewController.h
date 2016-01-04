//
//  AccessibilityStatementViewController.h
//  dementia
//
//
//

#import "BaseViewController.h"

@interface AccessibilityStatementViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UIWebView *accessibilityStatementWebView;

- (IBAction)closeBtnClick:(id)sender;
@end