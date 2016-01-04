//
//  SelfAssignmentResultViewController.m
//  dementia
//
//

#import "SelfAssignmentResultViewController.h"

#import "HomeViewController.h"

@interface SelfAssignmentResultViewController ()

@end

@implementation SelfAssignmentResultViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_SELF_ASSIGNMENT")];
    
    [self.pageTitleLbl setText:LocalizedString(@"SELF_ASSIGNMENT_PAGE_TITLE")];
    [self.resultMark setText:LocalizedString(@"SELF_ASSIGNMENT_RESULT")];
    
    //[self.callBtn setTitle:LocalizedString(@"BUTTON_SELF_ASSIGNMENT_CALL") forState:UIControlStateNormal];
    [self.retryBtn setTitle:LocalizedString(@"BUTTON_RETRY") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    [self.resultMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    
    [self.callBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.retryBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

- (void)setUpData {
    if ([self.yesQuestionArray count] <= 2) {
        UILabel *lbl = [[UILabel alloc] initWithFrame:CGRectMake(5, 0, [[UIScreen mainScreen] bounds].size.width - 10, 0)];
        [lbl setNumberOfLines:0];
        [lbl setLineBreakMode:NSLineBreakByWordWrapping];
        [lbl setText:[LocalizedString(@"SELF_ASSIGNMENT_RESULT_RECOMMEND_1") stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"]];
        [lbl setFont:[UIFont systemFontOfSize:19.0f]];
        [lbl sizeToFit];
        [self.scrollResult addSubview:lbl];
        
        [self.scrollResult setContentSize:CGSizeMake(self.scrollResult.frame.size.width, lbl.frame.size.height)];
    }
    else {
        NSString *conclusionString = [NSString stringWithFormat:@"%@", LocalizedString(@"SELF_ASSIGNMENT_RESULT_TITLE")];
        
        for (NSNumber *questionNumber in self.yesQuestionArray) {
            NSString *localizationKey = [NSString stringWithFormat:@"SELF_ASSIGNMENT_TITLE_%@", questionNumber];
            conclusionString = [conclusionString stringByAppendingString:[NSString stringWithFormat:@"\n\u2022%@", LocalizedString(localizationKey)]];
        }
        
        UILabel *lbl = [[UILabel alloc] initWithFrame:CGRectMake(0, 10, 0, 0)];
        [lbl setNumberOfLines:0];
        [lbl setLineBreakMode:NSLineBreakByWordWrapping];
        [lbl setText:conclusionString];
        [lbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        [lbl sizeToFit];
        [lbl setCenter:CGPointMake(self.view.center.x, lbl.center.y)];
        
        UIImageView *bgImgView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, lbl.frame.size.height + 20)];
        [bgImgView setContentMode:UIViewContentModeScaleToFill];
        [bgImgView setImage:[UIImage imageNamed:@"Background_SelfAssignment"]];
        
        [self.scrollResult addSubview:bgImgView];
        [self.scrollResult addSubview:lbl];
        
        UIWebView *webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, bgImgView.frame.size.height + 10, [[UIScreen mainScreen] bounds].size.width, 0)];
        [webView setDelegate:self];
        [webView.scrollView setBounces:NO];
        [webView setDataDetectorTypes:UIDataDetectorTypeNone];
        [webView loadHTMLString:LocalizedString(@"SELF_ASSIGNMENT_RESULT_RECOMMEND_2") baseURL:nil];
        [self.scrollResult addSubview:webView];
    }
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {    
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[HomeViewController class]]) {
            [self.navigationController popToViewController:vc animated:YES];
            break;
        }
    }
}

- (IBAction)callBtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"tel:26366323"]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel:26366323"]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)retryBtnClick:(id)sender {
    if (self.retryBlock != nil) {
        self.retryBlock(1);
    }
    
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - UIWebView delegate

- (BOOL)webView:(UIWebView *)inWeb shouldStartLoadWithRequest:(NSURLRequest *)inRequest navigationType:(UIWebViewNavigationType)inType {
    if (inType == UIWebViewNavigationTypeLinkClicked) {
        [[UIApplication sharedApplication] openURL:[inRequest URL]];
        
        return NO;
    }
    
    return YES;
}

- (void)webViewDidFinishLoad:(UIWebView *)webView {
    CGRect frame = webView.frame;
    frame.size.height = 1;
    webView.frame = frame;
    
    CGSize fittingSize = [webView sizeThatFits:CGSizeZero];
    frame.size = fittingSize;
    webView.frame = frame;
    
    [self.scrollResult setContentSize:CGSizeMake(self.scrollResult.frame.size.width, webView.frame.origin.y + webView.frame.size.height)];
}

@end
