//
//  MainViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface MainViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UILabel *tipsLbl;

@property (weak, nonatomic) IBOutlet UILabel *progressLbl;
@property (weak, nonatomic) IBOutlet UIProgressView *progressView;

@end
