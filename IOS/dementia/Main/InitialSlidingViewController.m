//
//  InitialSlidingViewController.m
//  dementia
//
//

#import "InitialSlidingViewController.h"

@interface InitialSlidingViewController ()

@end

@implementation InitialSlidingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.topViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"NavigationMain"];
    self.shouldAddPanGestureRecognizerToTopViewSnapshot = YES;
}

@end
