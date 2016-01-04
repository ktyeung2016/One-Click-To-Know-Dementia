//
//  AccessibilityStatementViewController.m
//  dementia
//
//
//

#import "AccessibilityStatementViewController.h"

@interface AccessibilityStatementViewController ()

@end

@implementation AccessibilityStatementViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.navigationController setNavigationBarHidden:YES];
    
    [self.accessibilityStatementWebView loadHTMLString:@"<p>本程式採用了無障礙設計。如在使用上有任何查詢或意見，請致電或發送電郵與我們聯絡。</p><p>電話號碼 : 26366323</p><p>電郵地址：info@jccpahk.com</p>" baseURL:nil];
}

- (IBAction)closeBtnClick:(id)sender {
    [self.navigationController setNavigationBarHidden:NO];
    [self.navigationController popViewControllerAnimated:YES];
}
@end