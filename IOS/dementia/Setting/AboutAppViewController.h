//
//  AboutAppViewController.h
//  dementia
//
//
//

#import "BaseViewController.h"

@interface AboutAppViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UIWebView *aboutAppWebView;

- (IBAction)closeBtnClick:(id)sender;
@end