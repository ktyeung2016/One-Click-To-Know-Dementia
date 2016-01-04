//
//  NewsViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface NewsViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UIWebView *newsWebView;

@property (strong, nonatomic) NSString *contentString;

- (IBAction)crossBtnClick:(id)sender;

@end
