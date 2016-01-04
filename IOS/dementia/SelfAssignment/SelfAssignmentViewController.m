//
//  SelfAssignmentViewController.m
//  dementia
//
//

#import "SelfAssignmentViewController.h"

#import "SelfAssignmentResultViewController.h"

@interface SelfAssignmentViewController ()

@property (assign, nonatomic) NSUInteger currentQuestionNumber;

@property (strong, nonatomic) NSMutableArray *yesQuestionArray;

@end

@implementation SelfAssignmentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.currentQuestionNumber = 1;
    
    self.yesQuestionArray = [NSMutableArray array];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_SELF_ASSIGNMENT")];
    
    [self.pageTitleLbl setText:LocalizedString(@"SELF_ASSIGNMENT_PAGE_TITLE")];
    [self.questionNumberMark1 setText:LocalizedString(@"SELF_ASSIGNMENT_QUESTION_NUMBER_1")];
    [self.questionNumberMark2 setText:LocalizedString(@"SELF_ASSIGNMENT_QUESTION_NUMBER_2")];
    [self.remarkLbl setText:LocalizedString(@"SELF_ASSIGNMENT_REMARK")];
    
    [self.yesBtn setTitle:LocalizedString(@"BUTTON_SELF_ASSIGNMENT_YES") forState:UIControlStateNormal];
    [self.noBtn setTitle:LocalizedString(@"BUTTON_SELF_ASSIGNMENT_NO") forState:UIControlStateNormal];
}

- (void)setUpData {
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.currentQuestionNumber]];
    
    NSString *titleLocalizationKey = [NSString stringWithFormat:@"SELF_ASSIGNMENT_TITLE_%lu", (unsigned long)self.currentQuestionNumber];
    NSString *contentLocalizationKey = [NSString stringWithFormat:@"SELF_ASSIGNMENT_CONTENT_%lu", (unsigned long)self.currentQuestionNumber];
    
    [self.contentTxtView setText:[NSString stringWithFormat:@"%@%@", LocalizedString(titleLocalizationKey), [[LocalizedString(contentLocalizationKey) stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"] stringByReplacingOccurrencesOfString:@"\\u2022" withString:@"\u2022"]]];
    [self.contentTxtView setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    
    [self.questionNumberMark1 setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    [self.questionNumberLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    [self.questionNumberMark2 setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    [self.remarkLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    
    [self.yesBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.noBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

#pragma mark - IBAction

- (IBAction)answerBtnClick:(id)sender {
    if ([sender tag] == 1) {
        [self.yesQuestionArray addObject:[NSNumber numberWithInteger:self.currentQuestionNumber]];
    }
    
    self.currentQuestionNumber++;
    
    if (self.currentQuestionNumber <= 10) {
        [self setUpData];
    }
    else {
        SelfAssignmentResultViewController *resultVC = [self.storyboard instantiateViewControllerWithIdentifier:@"SelfAssignmentResultViewController"];
        [resultVC setYesQuestionArray:self.yesQuestionArray];
        
        resultVC.retryBlock = ^(BOOL status) {
            if (status) {
                self.currentQuestionNumber = 1;
                [self.yesQuestionArray removeAllObjects];
                
                [self setUpData];
            }
        };
        
        [self.navigationController pushViewController:resultVC animated:YES];
    }
}

@end
