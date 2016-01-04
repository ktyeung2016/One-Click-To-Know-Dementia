//
//  GameQuestionNumberViewController.m
//  dementia
//
//

#import "GameQuestionNumberViewController.h"

@interface GameQuestionNumberViewController ()

@property (strong, nonatomic) NSTimer *dismissTimer;

@end

@implementation GameQuestionNumberViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.questionNumber]];
    
    self.dismissTimer = [NSTimer scheduledTimerWithTimeInterval:2 target:self selector:@selector(dismissSelf) userInfo:nil repeats:NO];
}

#pragma mark - NSTimer handling

- (void)dismissSelf {
    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
