//
//  BaseViewController.m
//  dementia
//
//

#import "BaseViewController.h"

#import "DrawerViewController.h"

#import "ECSlidingViewController/ECSlidingViewController.h"

@interface BaseViewController ()

@end

@implementation BaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpBackground];
    [self setUpNavigationView];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    if (![self.slidingViewController.underRightViewController isKindOfClass:[DrawerViewController class]]) {
        UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
        self.slidingViewController.underRightViewController  = [storyboard instantiateViewControllerWithIdentifier:@"DrawerViewController"];
    }    
}

- (void)setUpBackground {
    UIImageView *bgImgView = [[UIImageView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    [bgImgView setContentMode:UIViewContentModeScaleToFill];
    [bgImgView setImage:[UIImage imageNamed:[self backgroundImageName]]];
    
    [self.view addSubview:bgImgView];
    [self.view sendSubviewToBack:bgImgView];
}

- (void)setUpNavigationView {
    [self.navigationItem setHidesBackButton:YES];
    
    UIImage *backImg = [UIImage imageNamed:@"Button_Navigation_Back"];
    
    UIButton *leftBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, backImg.size.width, backImg.size.height)];
    [leftBtn setImage:backImg forState:UIControlStateNormal];
    [leftBtn setIsAccessibilityElement:TRUE];
    [leftBtn setAccessibilityLabel:@""];
    [leftBtn setAccessibilityHint:@"返回"];
    [leftBtn addTarget:self action:@selector(navigationBarBackBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    
    UIBarButtonItem *leftBarBtn = [[UIBarButtonItem alloc] initWithCustomView:leftBtn];
    
    [self.navigationItem setLeftBarButtonItem:leftBarBtn];
    
    UIImage *drawerImg = [UIImage imageNamed:@"Button_Navigation_Drawer"];
    
    UIButton *rightBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, drawerImg.size.width, drawerImg.size.height)];
    [rightBtn setImage:drawerImg forState:UIControlStateNormal];
    [rightBtn setIsAccessibilityElement:TRUE];
    [rightBtn setAccessibilityLabel:@""];
    [rightBtn setAccessibilityHint:@"選單"];
    [rightBtn addTarget:self action:@selector(navigationBarDrawerBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    
    UIBarButtonItem *rightBarBtn = [[UIBarButtonItem alloc] initWithCustomView:rightBtn];
    
    [self.navigationItem setRightBarButtonItem:rightBarBtn];
}

- (NSString *)backgroundImageName {
    return @"Background_Main";
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)navigationBarDrawerBtnClick:(id)sender {
    [self.slidingViewController anchorTopViewTo:ECLeft];
}

@end
