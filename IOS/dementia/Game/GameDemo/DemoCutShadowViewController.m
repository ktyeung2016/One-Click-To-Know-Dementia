//
//  DemoCutShadowViewController.m
//  dementia
//
//

#import "DemoCutShadowViewController.h"

@interface DemoCutShadowViewController ()

@property (assign, nonatomic) NSUInteger currentPageNumber;

@property (strong, nonatomic) NSArray *demoMsgList;

@end

@implementation DemoCutShadowViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.currentPageNumber = 1;
    
    self.demoMsgList = @[@"bottom", @"middle", @"bottom", @"middle", @"middle", @"bottom", @"bottom", @"bottom"];
    
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
    [self.navigationItem setTitle:LocalizedString(@"GAME_CUT_SHADOW")];
    
    [self.playMethodMark setText:LocalizedString(@"GAME_DEMO_PLAY_METHOD")];
    [self.pageTitleLbl setText:LocalizedString(@"GAME_CUT_SHADOW_TITLE")];
    
    [self.confirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.playMethodMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.pagesLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    
    [self.middleDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.bottomDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.confirmBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

- (void)setUpView {
    [self.pagesLbl setText:[NSString stringWithFormat:@"%lu/8", (unsigned long)self.currentPageNumber]];
    
    if (self.currentPageNumber >= 3) {
        [self.crossShadowImg setImage:[UIImage imageNamed:@"CutShadow001_shadow"]];
        [self.crossImg setHidden:YES];
    }
    else {
        [self.crossShadowImg setImage:[UIImage imageNamed:@"CutShadow001"]];
        [self.crossImg setHidden:NO];
    }
    
    if (self.currentPageNumber < 4) {
        [self.starShadowImg setImage:[UIImage imageNamed:@"CutShadow004"]];
        [self.starImg setImage:[UIImage imageNamed:@"CutShadow004_shadow"]];
    }
    else if (self.currentPageNumber == 4) {
        [self.starShadowImg setImage:[self addLightFilterToImage:[UIImage imageNamed:@"CutShadow004"]]];
        [self.starImg setImage:[self addLightFilterToImage:[UIImage imageNamed:@"CutShadow004_shadow"]]];
        [self.starImg setHidden:NO];
        
        [self.circleShadowImg setImage:[UIImage imageNamed:@"CutShadow002"]];
        [self.circleImg setHidden:NO];
        
        [self.confirmBtn setHidden:YES];
    }
    else if (self.currentPageNumber > 4) {
        [self.starShadowImg setImage:[self addLightFilterToImage:[UIImage imageNamed:@"CutShadow004_shadow"]]];
        [self.starImg setHidden:YES];
        
        [self.circleShadowImg setImage:[UIImage imageNamed:@"CutShadow002_shadow"]];
        [self.circleImg setHidden:YES];
        
        [self.confirmBtn setHidden:NO];
    }
    
    if (self.currentPageNumber >= 6) {
        [self.tickImg setHidden:NO];
        
        if (self.currentPageNumber == 6) {
            [self.tickImg setImage:[UIImage imageNamed:@"Tick"]];
        }
        else {
            [self.tickImg setImage:[UIImage imageNamed:@"Cross"]];
        }
    }
    else {
        [self.tickImg setHidden:YES];
    }
    
    if (self.currentPageNumber >= 7) {
        [self.crossShadowImg setImage:[UIImage imageNamed:@"CutShadow001"]];
        
        [self.crossImg2 setHidden:NO];
        
        [self.heartImg setImage:[UIImage imageNamed:@"Game_EmptyHeart"]];
    }
    else {
        [self.crossImg2 setHidden:YES];
        
        [self.heartImg setImage:[UIImage imageNamed:@"Game_Heart"]];
    }
    
    if (self.currentPageNumber == self.demoMsgList.count + 1) {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
         [db executeUpdate:@"UPDATE `game_demo` SET `shown` = '1' WHERE `game_type` = '2' AND `user_id` = ?", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]]];
        
        [db close];
        
        [self handleBackEvent];
    }
    
    if (self.currentPageNumber < self.demoMsgList.count + 1) {
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DEMO_CUT_SHADOW_MSG_%lu", (unsigned long)self.currentPageNumber];
        
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
    CGRect highlightRect3;
    if (self.currentPageNumber==1) {
        int offsetWidth = 120;
        if (screenRect.size.width<=320) {
            offsetWidth = 110;
        }
        highlightRect = CGRectMake(screenRect.size.width/2-offsetWidth, screenRect.size.height-104, offsetWidth*2, 40);
        highlightRect2 = CGRectMake(0, 30, screenRect.size.width, 30);
        highlightRect3 = CGRectMake(0, 60, 140, 140);
    } else if (self.currentPageNumber==2) {
        highlightRect = CGRectMake(8, screenRect.size.height-192, 200, 70);
    } else if (self.currentPageNumber==3) {
        highlightRect = CGRectMake(0, 60, 140, 140);
    } else if (self.currentPageNumber==4) {
        highlightRect = CGRectMake(8, screenRect.size.height-103, 37, 37);
    } else if (self.currentPageNumber==5) {
        highlightRect = CGRectMake(screenRect.size.width/2-50, screenRect.size.height-183, 100, 50);
    } else if (self.currentPageNumber==6) {
        highlightRect = CGRectMake(0, 60, 140, 140);
        highlightRect2 = CGRectMake(screenRect.size.width-140, screenRect.size.height-300, 140, 140);
    } else if (self.currentPageNumber==7) {
        int offsetWidth = 120;
        if (screenRect.size.width<=320) {
            offsetWidth = 110;
        }
        highlightRect = CGRectMake(screenRect.size.width/2-offsetWidth, screenRect.size.height-104, offsetWidth*2, 40);
        highlightRect2 = CGRectMake(0, 60, screenRect.size.width, 140);
        highlightRect3 = CGRectMake(screenRect.size.width-100, screenRect.size.height-310, 100, 140);
    } else if (self.currentPageNumber==8) {
    }
    if (highlightRect.size.width>0) {
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:highlightRect];
        [overlayPath appendPath:transparentPath];
    }
    if (highlightRect2.size.width>0) {
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:highlightRect2];
        [overlayPath appendPath:transparentPath];
    }
    if (highlightRect3.size.width>0) {
        UIBezierPath *transparentPath = [UIBezierPath bezierPathWithRect:highlightRect3];
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
        
        NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = 2", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
        
        [db close];
        
        if ([tempDemoDict[@"shown"] isEqualToString:@"1"]) {
            [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"GameCutShadowViewController"] animated:YES];
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

#pragma mark - UIImage add filter

- (UIImage *)addLightFilterToImage:(UIImage *)image {
    UIGraphicsBeginImageContext(image.size);
    
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    [[UIColor lightGrayColor] setFill];
    
    CGContextTranslateCTM(context, 0, image.size.height);
    CGContextScaleCTM(context, 1.0, -1.0);
    
    CGContextSetBlendMode(context, kCGBlendModeOverlay);
    CGRect rect = CGRectMake(0, 0, image.size.width, image.size.height);
    CGContextDrawImage(context, rect, image.CGImage);
    
    CGContextClipToMask(context, rect, image.CGImage);
    CGContextAddRect(context, rect);
    CGContextDrawPath(context,kCGPathFill);
    
    UIImage *coloredImg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return coloredImg;
}

@end
