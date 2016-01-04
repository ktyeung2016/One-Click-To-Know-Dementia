//
//  DemoBuyerViewController.m
//  dementia
//
//

#import "DemoBuyerViewController.h"

@interface DemoBuyerViewController ()

@property (assign, nonatomic) NSUInteger currentPageNumber;

@property (strong, nonatomic) NSArray *demoMsgList;

@end

@implementation DemoBuyerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.currentPageNumber = 1;
    
    self.demoMsgList = @[@"bottom", @"bottom", @"bottom", @"", @"bottom", @"bottom", @"bottom", @"", @"bottom"];
    
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
    [self.navigationItem setTitle:LocalizedString(@"GAME_BUYER")];
    
    [self.playMethodMark setText:LocalizedString(@"GAME_DEMO_PLAY_METHOD")];
}

- (void)setUpTextFontSize {
    [self.playMethodMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.pagesLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    
    for (UILabel *lbl in self.dayLbl) {
        [lbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    }
    
    [self.confirmBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

- (void)setUpView {
    [self.pagesLbl setText:[NSString stringWithFormat:@"%lu/9", (unsigned long)self.currentPageNumber]];
    
    if (self.currentPageNumber < 2 || self.currentPageNumber == 8) {
        [self.memorizeView setHidden:NO];
        [self.dayView setHidden:YES];
        [self.thingsView setHidden:YES];
        
        [self.pageTitleLbl setText:LocalizedString(@"GAME_BUYER_TITLE_1")];
    }
    else if (self.currentPageNumber == 2) {
        [self.memorizeView setHidden:YES];
        [self.dayView setHidden:NO];
        [self.thingsView setHidden:YES];
        
        [self.pageTitleLbl setText:LocalizedString(@"GAME_BUYER_TITLE_2")];
    }
    else {
        [self.memorizeView setHidden:YES];
        [self.dayView setHidden:YES];
        [self.thingsView setHidden:NO];
        
        [self.pageTitleLbl setText:LocalizedString(@"GAME_BUYER_TITLE_3")];
    }
    
    if (self.currentPageNumber == 4) {
        [self.thingsFirstOutline setImage:[UIImage imageNamed:@"Game1_Outline_Tick"]];
    }
    else if (self.currentPageNumber == 6) {
        [self.thingsFirstOutline setImage:[UIImage imageNamed:@"Game1_Outline_Cross"]];
    }
    else {
        [self.thingsFirstOutline setImage:[UIImage imageNamed:@"Game1_Outline"]];
    }
    
    if (self.currentPageNumber == 5) {
        [self.thingsSecondOutline setImage:[UIImage imageNamed:@"Game1_Outline_Cross"]];
    }
    else {
        [self.thingsSecondOutline setImage:[UIImage imageNamed:@"Game1_Outline"]];
    }
    
    if (self.currentPageNumber == 8) {
        [self.memorizeFirstOutline setImage:[UIImage imageNamed:@"Game1_Outline_Tick"]];
        [self.tipsView setHidden:NO];
    }
    else {
        [self.memorizeFirstOutline setImage:[UIImage imageNamed:@"Game1_Outline"]];
        [self.tipsView setHidden:YES];
    }
    
    if (self.currentPageNumber == self.demoMsgList.count + 1) {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
         [db executeUpdate:@"UPDATE `game_demo` SET `shown` = '1' WHERE `game_type` = '1' AND `user_id` = ?", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]]];
        
        [db close];
        
        [self handleBackEvent];
    }
    
    if (self.currentPageNumber < self.demoMsgList.count + 1) {
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DEMO_BUYER_MSG_%lu", (unsigned long)self.currentPageNumber];
        
        if ([self.demoMsgList[self.currentPageNumber - 1] isEqualToString:@"bottom"]) {
            [self.bottomDemoView setHidden:NO];
            
            [self.bottomDemoLbl setText:LocalizedString(localizationKey)];
        }
        else {
            [self.bottomDemoView setHidden:YES];
        }
    }
    
    UIBezierPath *overlayPath = [UIBezierPath bezierPathWithRect:self.view.bounds];
    
    CGRect screenRect = [UIScreen mainScreen].bounds;
    CGRect highlightRect;
    CGRect highlightRect2;
    if (self.currentPageNumber==1) {
        highlightRect = CGRectMake(0, screenRect.size.height/2-90, screenRect.size.width, 120);
    } else if (self.currentPageNumber==2) {
        highlightRect = CGRectMake(0, 50, screenRect.size.width, 40);
        highlightRect2 = CGRectMake(0, screenRect.size.height/2-150, screenRect.size.width, 250);
    } else if (self.currentPageNumber==3) {
        int offsetWidth = 120;
        if (screenRect.size.width<=320) {
            offsetWidth = 110;
        }
        highlightRect = CGRectMake(screenRect.size.width/2-offsetWidth, screenRect.size.height-104, offsetWidth*2, 40);
    } else if (self.currentPageNumber==4) {
        highlightRect = CGRectMake(screenRect.size.width/2-95, screenRect.size.height/2-151, 190, 230);
    } else if (self.currentPageNumber==5) {
        highlightRect = CGRectMake(screenRect.size.width/2, screenRect.size.height/2-151, 90, 110);
    } else if (self.currentPageNumber==6) {
        highlightRect = CGRectMake(screenRect.size.width/2-95, screenRect.size.height/2-151, 90, 110);
    } else if (self.currentPageNumber==7) {
        highlightRect = CGRectMake(screenRect.size.width-46, screenRect.size.height-104, 40, 40);
    } else if (self.currentPageNumber==8) {
        highlightRect = CGRectMake(0, screenRect.size.height/2-100, screenRect.size.width, 130);
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
        
        NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = 1", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
        
        [db close];
        
        if ([tempDemoDict[@"shown"] isEqualToString:@"1"]) {
            [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"GameBuyerViewController"] animated:YES];
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
