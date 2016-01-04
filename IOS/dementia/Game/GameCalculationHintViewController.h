//
//  GameCalculationHintViewController.h
//  dementia
//
//
//

#import "BaseViewController.h"

@interface GameCalculationHintViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UIWebView *calculationHintWebView;
@property (nonatomic) NSUInteger totalBudget;

- (IBAction)closeBtnClick:(id)sender;
@end