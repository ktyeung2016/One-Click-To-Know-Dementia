//
//  KnowledgeDetailViewController.m
//  dementia
//
//

#import "KnowledgeDetailViewController.h"

@interface KnowledgeDetailViewController ()

@property (strong, nonatomic) UILabel *knowledgeTitleLbl;
@property (strong, nonatomic) UIWebView *knowledgeWebView;

@end

@implementation KnowledgeDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self setUpView];
}

- (void)setUpView {
    self.knowledgeTitleLbl = [[UILabel alloc] initWithFrame:CGRectMake(10, 12, [[UIScreen mainScreen] bounds].size.width - 20, 0)];
    [self.knowledgeTitleLbl setLineBreakMode:NSLineBreakByWordWrapping];
    [self.knowledgeTitleLbl setNumberOfLines:0];
    [self.knowledgeTitleLbl setTextColor:[UIColor colorWithRed:0.2 green:0.44 blue:0.41 alpha:1]];
    [self.knowledgeTitleLbl setTextAlignment:NSTextAlignmentCenter];
    [self.knowledgeTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    [self.knowledgeTitleLbl setText:self.detailDict[[NSString stringWithFormat:@"title_%@", LanguageForDataAttribute]]];
    [self.knowledgeTitleLbl sizeToFit];
    [self.knowledgeTitleLbl setCenter:CGPointMake(self.view.center.x, self.knowledgeTitleLbl.center.y)];
    [self.view addSubview:self.knowledgeTitleLbl];
    
    self.knowledgeWebView = [[UIWebView alloc] initWithFrame:CGRectMake(0,
                                                                        self.knowledgeTitleLbl.frame.origin.y + self.knowledgeTitleLbl.frame.size.height + 12,
                                                                        self.view.frame.size.width,
                                                                        self.view.frame.size.height - self.knowledgeTitleLbl.frame.origin.y - self.knowledgeTitleLbl.frame.size.height - 12)];
    [self.knowledgeWebView.scrollView setBounces:NO];
    [self.knowledgeWebView setDelegate:self];
    [self.knowledgeWebView loadHTMLString:self.detailDict[[NSString stringWithFormat:@"html_content_%@", LanguageForDataAttribute]] baseURL:nil];
    [self.view addSubview:self.knowledgeWebView];
}

- (void)removeView {
    if (self.knowledgeTitleLbl != nil) {
        [self.knowledgeTitleLbl removeFromSuperview];
        self.knowledgeTitleLbl = nil;
    }
    
    if (self.knowledgeWebView != nil) {
        [self.knowledgeWebView removeFromSuperview];
        self.knowledgeWebView = nil;
    }
}

#pragma mark - UIWebView delegate

- (BOOL)webView:(UIWebView *)inWeb shouldStartLoadWithRequest:(NSURLRequest *)inRequest navigationType:(UIWebViewNavigationType)inType {
    if (inType == UIWebViewNavigationTypeLinkClicked) {
        
        NSArray *splitString = [[[inRequest URL] absoluteString] componentsSeparatedByString:@"://"];
        
        if ([splitString[0] isEqualToString:@"dementia"]) {
            FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
            [db open];
        
            NSDictionary *tempDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `knowledge` WHERE `id` = ?", splitString[1]] keys:KnowledgeTableAttributes].firstObject;
            
            if (tempDict != nil && ![tempDict isEqual:@{}]) {
                self.detailDict = tempDict;
                [self removeView];
                [self setUpView];
            }
            
            [db close];
        }
        else {
            [[UIApplication sharedApplication] openURL:[inRequest URL]];
        }
        
        return NO;
    }
    
    return YES;
}

@end
