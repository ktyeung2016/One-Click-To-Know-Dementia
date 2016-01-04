//
//  GameCalculationHintViewController.m
//  dementia
//
//
//

#import "GameCalculationHintViewController.h"

@interface GameCalculationHintViewController ()

@end

@implementation GameCalculationHintViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.navigationController setNavigationBarHidden:YES];
    
    [self.calculationHintWebView loadHTMLString:[NSString stringWithFormat:@"<p>尚有餘額：$%lu</p>", (unsigned long)self.totalBudget] baseURL:nil];
}

- (IBAction)closeBtnClick:(id)sender {
    [self.navigationController setNavigationBarHidden:NO];
    [self.navigationController popViewControllerAnimated:YES];
}
@end