//
//  DemoCalculationViewController.m
//  dementia
//
//

#import "DemoCalculationViewController.h"

@interface DemoCalculationViewController ()

@property (assign, nonatomic) NSUInteger currentPageNumber;

@property (strong, nonatomic) NSArray *demoMsgList;

@end

@implementation DemoCalculationViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.currentPageNumber = 1;
    
    self.demoMsgList = @[@"bottom", @"middle", @"middle", @"middle", @"middle", @"bottom", @"middle", @"middle", @"bottom"];
    
    [self.middleDemoBtn.titleLabel setTextAlignment:NSTextAlignmentCenter];
    
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
    [self.navigationItem setTitle:LocalizedString(@"GAME_CALCULATION")];
    
    [self.playMethodMark setText:LocalizedString(@"GAME_DEMO_PLAY_METHOD")];
    
    [self.startBtn setTitle:LocalizedString(@"BUTTON_START") forState:UIControlStateNormal];
    
    [self.itemsCostViewItemsMark setText:LocalizedString(@"GAME_CALCULATION_ITEM")];
    [self.itemsCostViewPriceMark setText:LocalizedString(@"GAME_CALCULATION_PRICE")];
    [self.itemsCostViewQuantityMark setText:LocalizedString(@"GAME_CALCULATION_QUANTITY")];
    [self.itemsCostViewTotalMark setText:LocalizedString(@"GAME_CALCULATION_TOTAL_ITEM_COST")];
    [self.itemsCostViewConfirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
    
    [self.leftCostViewTotalPriceMark setText:LocalizedString(@"GAME_CALCULATION_TOTAL_LEFT_COST")];
    [self.leftCostMark setText:LocalizedString(@"GAME_CALCULATION_LEFT_COST")];
    [self.leftCostViewConfirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.playMethodMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.pagesLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.memorizeMoneyLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    [self.startBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    [self.itemsCostViewItemsMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    [self.itemsCostViewPriceMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    [self.itemsCostViewQuantityMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    [self.itemsCostViewTotalMark setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    for (int i=0; i<[self.itemsPriceLbl count]; i++) {
        [self.itemsPriceLbl[i] setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        [self.itemsQuantityLbl[i] setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    }
    
    [self.itemsCostTxtField setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.itemsCostViewConfirmBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    [self.leftCostViewTotalPriceMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.leftCostMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.leftCostTxtField setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.leftCostViewConfirmBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.bottomDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
}

- (void)setUpView {
    [self.pagesLbl setText:[NSString stringWithFormat:@"%lu/9", (unsigned long)self.currentPageNumber]];
    
    if (self.currentPageNumber < 3) {
        [self.memorizeMoneyView setHidden:NO];
        [self.calculateItemsCostView setHidden:YES];
        [self.calculateLeftCostView setHidden:YES];
        
        [self.pageTitleLbl setText:LocalizedString(@"GAME_CALCULATION_TITLE_1")];
    }
    else if (self.currentPageNumber >= 3 && self.currentPageNumber < 6) {
        [self.memorizeMoneyView setHidden:YES];
        [self.calculateItemsCostView setHidden:NO];
        [self.calculateLeftCostView setHidden:YES];
        
        [self.pageTitleLbl setText:LocalizedString(@"GAME_CALCULATION_TITLE_2")];
    }
    else {
        [self.memorizeMoneyView setHidden:YES];
        [self.calculateItemsCostView setHidden:YES];
        [self.calculateLeftCostView setHidden:NO];
        
        [self.pageTitleLbl setText:LocalizedString(@"GAME_CALCULATION_TITLE_3")];
    }
    
    if (self.currentPageNumber < 4) {
        [self.itemsCostTxtField setText:@""];
    }
    else {
        [self.itemsCostTxtField setText:@"36"];
    }
    
    if (self.currentPageNumber < 7) {
        [self.leftCostTxtField setText:@""];
    }
    else {
        [self.leftCostTxtField setText:@"64"];
    }
    
    if (self.currentPageNumber == self.demoMsgList.count + 1) {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
         [db executeUpdate:@"UPDATE `game_demo` SET `shown` = '1' WHERE `game_type` = '5' AND `user_id` = ?", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]]];
        
        [db close];
        
        [self handleBackEvent];
    }
    
    if (self.currentPageNumber < self.demoMsgList.count + 1) {
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DEMO_CALCULATION_MSG_%lu", (unsigned long)self.currentPageNumber];
        
        if ([self.demoMsgList[self.currentPageNumber - 1] isEqualToString:@"middle"]) {
            [self.middleDemoView setHidden:NO];
            [self.bottomDemoView setHidden:YES];
            
            [self.middleDemoBtn setTitle:LocalizedString(localizationKey) forState:UIControlStateNormal];
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
        highlightRect2 = CGRectMake(0, 30, screenRect.size.width, 220);
    } else if (self.currentPageNumber==2) {
        highlightRect = CGRectMake(screenRect.size.width/2-50, screenRect.size.height-170, 100, 50);
    } else if (self.currentPageNumber==3) {
        highlightRect = CGRectMake(91, screenRect.size.height-154, 80, 40);
        highlightRect2 = CGRectMake(0, 90, screenRect.size.width, 200);
    } else if (self.currentPageNumber==4) {
        highlightRect = CGRectMake(screenRect.size.width-135, screenRect.size.height-159, 110, 50);
    } else if (self.currentPageNumber==5) {
    } else if (self.currentPageNumber==6) {
        int offsetHeight = 35;
        if (screenRect.size.height<=568) {
            offsetHeight = 15;
        }
        highlightRect = CGRectMake(screenRect.size.width/2-52, screenRect.size.height/2-offsetHeight, 122, 35);
    } else if (self.currentPageNumber==7) {
    } else if (self.currentPageNumber==8) {
        highlightRect = CGRectMake(screenRect.size.width/2-50, screenRect.size.height-160, 100, 50);
    } else if (self.currentPageNumber==9) {
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
        
        NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = 5", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
        
        [db close];
        
        if ([tempDemoDict[@"shown"] isEqualToString:@"1"]) {
            [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"GameCalculationViewController"] animated:YES];
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
