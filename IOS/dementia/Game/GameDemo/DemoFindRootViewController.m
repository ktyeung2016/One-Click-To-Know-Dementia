//
//  DemoFindRootViewController.m
//  dementia
//
//

#import "DemoFindRootViewController.h"

@interface DemoFindRootViewController ()

@property (assign, nonatomic) NSUInteger currentPageNumber;

@property (strong, nonatomic) NSArray *demoMsgList;

@end

@implementation DemoFindRootViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.currentPageNumber = 1;
    
    self.demoMsgList = @[@"bottom", @"middle", @"middle", @"bottom", @"", @"bottom"];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self setUpView];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    if (self.popFromDemoBlock != nil) {
        self.popFromDemoBlock(true);
    }
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"GAME_FIND_ROOT")];
    
    [self.playMethodMark setText:LocalizedString(@"GAME_DEMO_PLAY_METHOD")];
    [self.pageTitleLbl setText:LocalizedString(@"GAME_FIND_ROOT_TITLE")];
}

- (void)setUpTextFontSize {
    [self.playMethodMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.pagesLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    
    [self.middleDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.bottomDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
}

- (void)setUpView {
    [self.pagesLbl setText:[NSString stringWithFormat:@"%lu/6", (unsigned long)self.currentPageNumber]];
    
    if (self.currentPageNumber >= 3) {
        [self.heartImg setImage:[UIImage imageNamed:@"Game_EmptyHeart"]];
    }
    else {
        [self.heartImg setImage:[UIImage imageNamed:@"Game_Heart"]];
    }
    
    if (self.currentPageNumber >= 5) {
        [self.answerBtn setHidden:YES];
    }
    else {
        [self.answerBtn setHidden:NO];
    }
    
    if (self.currentPageNumber == self.demoMsgList.count + 1) {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
         [db executeUpdate:@"UPDATE `game_demo` SET `shown` = '1' WHERE `game_type` = '3' AND `user_id` = ?", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]]];
        
        [db close];
        
        [self handleBackEvent];
    }
    
    if (self.currentPageNumber < self.demoMsgList.count + 1) {
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DEMO_FIND_ROOT_MSG_%lu", (unsigned long)self.currentPageNumber];
        
        if ([self.demoMsgList[self.currentPageNumber - 1] isEqualToString:@"middle"]) {
            [self.middleDemoView setHidden:NO];
            [self.bottomDemoView setHidden:YES];
            
            [self.middleDemoLbl setText:LocalizedString(localizationKey)];
        }
        else if ([self.demoMsgList[self.currentPageNumber - 1] isEqualToString:@"bottom"]) {
            [self.middleDemoView setHidden:YES];
            [self.bottomDemoView setHidden:NO];
            
            [self.bottomDemoLbl setText:LocalizedString(localizationKey)];
        }
        else {
            [self.middleDemoView setHidden:YES];
            [self.bottomDemoView setHidden:YES];
        }
    }
    
    UIBezierPath *overlayPath = [UIBezierPath bezierPathWithRect:self.view.bounds];
    
    CGRect screenRect = [UIScreen mainScreen].bounds;
    CGRect highlightRect;
    CGRect highlightRect2;
    if (self.currentPageNumber==1) {
        int offsetWidth = 120;
        if (screenRect.size.width<=320) {
            offsetWidth = 110;
        }
        highlightRect = CGRectMake(screenRect.size.width/2-offsetWidth, screenRect.size.height-104, offsetWidth*2, 40);
        highlightRect2 = CGRectMake(0, 30, screenRect.size.width, 410);
    } else if (self.currentPageNumber==2) {
        highlightRect = CGRectMake(0, screenRect.size.height-200, screenRect.size.width, 85);
    } else if (self.currentPageNumber==3) {
        int offsetWidth = 120;
        if (screenRect.size.width<=320) {
            offsetWidth = 110;
        }
        highlightRect = CGRectMake(screenRect.size.width/2-offsetWidth, screenRect.size.height-104, offsetWidth*2, 45);
    } else if (self.currentPageNumber==4) {
        highlightRect = CGRectMake(8, screenRect.size.height-103, 37, 37);
    } else if (self.currentPageNumber==5) {
        highlightRect = CGRectMake(0, screenRect.size.height-200, screenRect.size.width, 85);
    } else if (self.currentPageNumber==6) {
    }
    if (highlightRect.size.width>0) {
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:highlightRect];
        [overlayPath appendPath:transparentPath];
    }
    if (highlightRect2.size.width>0) {
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:highlightRect2];
        [overlayPath appendPath:transparentPath];
    }
    [overlayPath setUsesEvenOddFillRule:true];
    
    CAShapeLayer *dimLayer = [CAShapeLayer layer];
    dimLayer.path = overlayPath.CGPath;
    dimLayer.fillRule = kCAFillRuleEvenOdd;
    dimLayer.fillColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.7].CGColor;
    
    for (CALayer *layer in self.demoLayerMask.layer.sublayers) {
        [layer removeFromSuperlayer];
    }
    [self.demoLayerMask.layer addSublayer:dimLayer];
}

- (void)handleBackEvent {
    if ([self.navigationController.viewControllers[[self.navigationController.viewControllers count] - 2] isKindOfClass:NSClassFromString(@"GameListViewController")]) {
        
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = 3", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
        
        [db close];
        
        if ([tempDemoDict[@"shown"] isEqualToString:@"1"]) {
            [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"GameFindRootViewController"] animated:YES];
        }
        else {
            [self.navigationController popViewControllerAnimated:YES];
        }
    }
    else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    [self handleBackEvent];
}

- (IBAction)backBtnClick:(id)sender {
    if (self.currentPageNumber > 1) {
        self.currentPageNumber--;
        
        [self setUpView];
    }
}

- (IBAction)nextBtnClick:(id)sender {
    self.currentPageNumber++;
    
    [self setUpView];
}

#pragma mark - UIView touch event

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    self.currentPageNumber++;
    
    [self setUpView];
}

@end