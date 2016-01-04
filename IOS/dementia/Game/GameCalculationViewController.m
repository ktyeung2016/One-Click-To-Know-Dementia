//
//  GameCalculationViewController.m
//  dementia
//
//

#import "GameCalculationViewController.h"

#import "GameQuestionNumberViewController.h"
#import "GameUpgradeViewController.h"
#import "GameEndViewController.h"

#import "DemoCalculationViewController.h"
#import "GameCalculationHintViewController.h"

#import "UIView+UIComponent.h"

#define MaxBudget 1000
#define MaxCouponDiscount 50
#define TotalThingsNumber 30

enum {
    GameStageMemorizeBudget = 0,
    GameStageCalculateItems = 1,
    GameStageCalculateLeftBudget = 2
};
typedef NSUInteger GameStage;

@interface GameCalculationViewController ()

@property (assign, nonatomic) NSUInteger levelNumber;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger correctNumber;
@property (assign, nonatomic) NSUInteger heartNumber;
@property (assign, nonatomic) NSUInteger totalScore;

@property (assign, nonatomic) NSUInteger totalBudget;
@property (assign, nonatomic) NSUInteger itemsTotal;

@property (strong, nonatomic) NSDictionary *requirementDict;

@property (strong, nonatomic) NSMutableArray *itemPriceList;
@property (strong, nonatomic) NSMutableArray *itemQuantityList;

@property (assign, nonatomic) GameStage currentStage;

@property (strong, nonatomic) NSTimer *nextQuestionTimer;

@end

@implementation GameCalculationViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSDictionary *tempLevelDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ? AND `game_type` = '5'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameLevelTableAttributes].firstObject;
    
    self.levelNumber = (tempLevelDict[@"level"] == nil) ? 1 : [tempLevelDict[@"level"] integerValue];
    
    self.requirementDict = @{@"ItemNum": @[@2, @3, @3],
                             @"PieceNum": @[@1, @3, @9],
                             @"PiecePrice": @[@9, @25, @50],
                             @"Threshold": @[@15, @100, @200],
                             @"MaxTotal": @[@100, @200, @500],
                             @"Discount": @[@NO, @YES, @YES]};
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self.itemsCostTxtField setTextFieldEdge];
    [self.leftCostTxtField setTextFieldEdge];
    
    [self initVariable];
    [self setUpMemorizeView];
    
    NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = '5'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
    
    if ([tempDemoDict[@"shown"] isEqualToString:@"0"]) {
        [self.questionBtn sendActionsForControlEvents:UIControlEventTouchUpInside];
    }
    
    [db close];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:@"UIKeyboardWillShowNotification"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:@"UIKeyboardWillHideNotification"
                                               object:nil];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"UIKeyboardWillShowNotification" object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"UIKeyboardWillHideNotification" object:nil];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"GAME_CALCULATION")];
    
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
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    
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
}

- (void)initVariable {
    self.questionNumber = 1;
    self.correctNumber = 0;
    self.heartNumber = 5;
    self.totalScore = 100;
    
    self.totalBudget = 0;
    
    for (UIImageView *tempImgView in self.heartImg) {
        [tempImgView setImage:[UIImage imageNamed:@"Game_Heart"]];
    }
    
    [GameEndViewController submitMark:5 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
}

- (void)setUpPageTitleLbl {
    NSString *localizationKey = [NSString stringWithFormat:@"GAME_CALCULATION_TITLE_%lu", self.currentStage + 1];
    [self.pageTitleLbl setText:LocalizedString(localizationKey)];
}

- (void)setUpMemorizeView {
    self.currentStage = GameStageMemorizeBudget;
    
    [self.memorizeMoneyView setHidden:NO];
    [self.calculateItemsCostView setHidden:YES];
    [self.calculateLeftCostView setHidden:YES];
    
    [self setUpPageTitleLbl];
    
    /*
     Show question number view
     */
    GameQuestionNumberViewController *questionNumberVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameQuestionNumberViewController"];
    [questionNumberVC setQuestionNumber:self.questionNumber];
    
    [questionNumberVC setModalTransitionStyle:UIModalTransitionStyleCrossDissolve];
    
    [[UIApplication sharedApplication] keyWindow].rootViewController.providesPresentationContextTransitionStyle = YES;
    [[UIApplication sharedApplication] keyWindow].rootViewController.definesPresentationContext = YES;
    
    if (IsIOS8) {
        [questionNumberVC setModalPresentationStyle:UIModalPresentationOverFullScreen];
    }
    else {
        [[UIApplication sharedApplication] keyWindow].rootViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
    }
    
    [self.navigationController presentViewController:questionNumberVC animated:NO completion:nil];
    
    /*
     Random budget
     */
    NSUInteger targetThreshold = [self.requirementDict[@"Threshold"][self.levelNumber - 1] integerValue];
    
    if (self.totalBudget < targetThreshold) {
        NSNumber *randomNumber = @(arc4random_uniform(1000));
        if ([randomNumber integerValue] < targetThreshold) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = @(arc4random_uniform(1000));
                
                if ([newRandomNumber integerValue] >= targetThreshold) {
                    self.totalBudget = [newRandomNumber integerValue];
                    
                    break;
                }
            }
        }
        else {
            self.totalBudget = [randomNumber integerValue];
        }
    }
    
    [self.memorizeMoneyLbl setText:[[NSString stringWithFormat:LocalizedString(@"GAME_CALCULATION_BUDGET"), self.totalBudget] stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"]];
}

- (void)setUpCalculateItemsCostView {
    self.currentStage = GameStageCalculateItems;
    
    [self.memorizeMoneyView setHidden:YES];
    [self.calculateItemsCostView setHidden:NO];
    [self.calculateLeftCostView setHidden:YES];
    
    [self setUpPageTitleLbl];
    
    if (self.levelNumber == 1 && !self.thirdItemView.hidden) {
        [self.thirdItemView setHidden:YES];
    }
    else if (self.levelNumber != 1 && self.thirdItemView.hidden) {
        [self.thirdItemView setHidden:NO];
    }
    
    if (self.levelNumber != 1 && self.thirdOutlineImgView.hidden) {
        [self.thirdOutlineImgView setHidden:NO];
    }
    
    /*
     Random
     */
    for (int i=0; i<[self.requirementDict[@"ItemNum"][self.levelNumber - 1] integerValue]; i++) {
        NSUInteger randomItem = arc4random_uniform(30) + 1;
        [self.itemsPhotoImgView[i] setImage:[UIImage imageNamed:[NSString stringWithFormat:(randomItem < 10) ? @"Game1_a00%lu" : @"Game1_a0%lu", (unsigned long)randomItem]]];
    }

    self.itemPriceList = [NSMutableArray arrayWithArray:
                          [self randomNumberWithQuantity:[self.requirementDict[@"ItemNum"][self.levelNumber - 1] integerValue]
                                              rangeStart:[self.requirementDict[@"PiecePrice"][self.levelNumber - 1] integerValue]
                                                rangeEnd:1]];
    
    self.itemQuantityList = [NSMutableArray arrayWithArray:
                             [self randomNumberWithQuantity:[self.requirementDict[@"ItemNum"][self.levelNumber - 1] integerValue]
                                                 rangeStart:[self.requirementDict[@"PieceNum"][self.levelNumber - 1] integerValue]
                                                   rangeEnd:1]];
    
    if ([self.requirementDict[@"Discount"][self.levelNumber - 1] boolValue]) {
        if (arc4random() % 2 == 0) {
            [self.itemsPhotoImgView[2] setImage:[UIImage imageNamed:@"Game5_Discount"]];
            [self.thirdOutlineImgView setHidden:YES];
            
            NSInteger price = arc4random_uniform(50) + 1;
            [self.itemPriceList replaceObjectAtIndex:2 withObject:@(-price)];
            [self.itemQuantityList replaceObjectAtIndex:2 withObject:@(1)];
        }
    }
    
    NSUInteger totalPrice;
    while (@(totalPrice)) {
        totalPrice = 0;
        for (int i=0; i<[self.itemPriceList count]; i++) {
            totalPrice += [self.itemPriceList[i] integerValue] * [self.itemQuantityList[i] integerValue];
        }
        
        if (totalPrice > [self.requirementDict[@"MaxTotal"][self.levelNumber - 1] integerValue]) {
            [self setUpCalculateItemsCostView];
            
        }
        else {
            for (int i=0; i<[self.itemPriceList count]; i++) {
                [self.itemsPriceLbl[i] setText:[NSString stringWithFormat:@"$%@", self.itemPriceList[i]]];
                [self.itemsQuantityLbl[i] setText:[NSString stringWithFormat:@"%@", self.itemQuantityList[i]]];
            }
            
            self.itemsTotal = totalPrice;
            
            break;
        }
    }
}

- (void)setUpCalculateLeftCostView {
    self.currentStage = GameStageCalculateLeftBudget;
    
    [self.memorizeMoneyView setHidden:YES];
    [self.calculateItemsCostView setHidden:YES];
    [self.calculateLeftCostView setHidden:NO];
    
    [self setUpPageTitleLbl];
    
    [self.leftCostLbl setText:[NSString stringWithFormat:@"$%lu", (unsigned long)self.itemsTotal]];
}

- (void)showResultAndNextSection:(BOOL)result {
    if (result) {
        [self.view endEditing:YES];
        
        if (self.currentStage == GameStageCalculateItems) {
            [self.itemsCostTxtField setText:@""];
            
            [self setUpCalculateLeftCostView];
        }
        else if (self.currentStage == GameStageCalculateLeftBudget) {
            [self.leftCostTxtField setText:@""];
            
            self.totalBudget -= self.itemsTotal;
            
            NSUInteger addMarks = 0;
            switch (self.levelNumber) {
                case 1:
                    addMarks = 10;
                    break;
                    
                case 2:
                    addMarks = 30;
                    break;
                    
                case 3:
                    addMarks = 50;
                    break;
            }
            self.totalScore += addMarks;
            
            self.correctNumber++;
            
            if (self.heartNumber > 0) {
                self.questionNumber++;
                
                if (self.levelNumber < 3 && self.correctNumber % GameQuesionNumberToNext == 0) {
                    self.levelNumber++;
                    [self showUpgradeView];
                }
                else {
                    self.nextQuestionTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(goToNextQuestion) userInfo:nil repeats:NO];
                }
            }
        }
    }
    else {
        self.heartNumber--;
        
        [self.heartImg[self.heartNumber] setImage:[UIImage imageNamed:@"Game_EmptyHeart"]];

        [GameEndViewController submitMark:5 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];

        if (self.heartNumber == 0) {
            GameEndViewController *gameEndVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameEndViewController"];
            [gameEndVC setPageTitle:self.navigationItem.title];
            [gameEndVC setType:5];
            [gameEndVC setQuestionNumber:self.correctNumber];
            [gameEndVC setScore:self.totalScore];
            [gameEndVC setDifficulty:self.levelNumber];
            
            gameEndVC.retryBlock = ^(BOOL status) {
                [self initVariable];
                
                [self setUpMemorizeView];
            };
            
            [self.navigationController pushViewController:gameEndVC animated:YES];
        }
    }
}

- (void)showUpgradeView {
    [GameEndViewController submitMark:5 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    GameUpgradeViewController *upgradeVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameUpgradeViewController"];
    [upgradeVC setType:5];
    [upgradeVC setDifficulty:self.levelNumber];
    
    upgradeVC.confirmBlock = ^(BOOL status) {
        [self setUpMemorizeView];
    };
    
    [upgradeVC setModalTransitionStyle:UIModalTransitionStyleCrossDissolve];
    
    [[UIApplication sharedApplication] keyWindow].rootViewController.providesPresentationContextTransitionStyle = YES;
    [[UIApplication sharedApplication] keyWindow].rootViewController.definesPresentationContext = YES;
    
    if (IsIOS8) {
        [upgradeVC setModalPresentationStyle:UIModalPresentationOverFullScreen];
    } else {
        [[UIApplication sharedApplication] keyWindow].rootViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
    }
    
    [self.navigationController presentViewController:upgradeVC animated:NO completion:nil];
}


#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    [self.navigationController popToViewController:self.navigationController.viewControllers[3] animated:YES];
}

- (IBAction)startBtnClick:(id)sender {
    [self setUpCalculateItemsCostView];
}

- (IBAction)itemsCostViewConfirmBtnClick:(id)sender {
    [self showResultAndNextSection:[self.itemsCostTxtField.text integerValue] == self.itemsTotal];
}

- (IBAction)leftCostViewConfirmBtnClick:(id)sender {
    [self showResultAndNextSection:[self.leftCostTxtField.text integerValue] == self.totalBudget - self.itemsTotal];
}

- (IBAction)informationBtnClick:(id)sender {
    GameCalculationHintViewController *targetController = [self.storyboard instantiateViewControllerWithIdentifier:@"GameCalculationHintViewController"];
    targetController.totalBudget = self.totalBudget;
    [self.navigationController pushViewController:targetController animated:YES];
}

- (IBAction)questionBtnClick:(id)sender {
    DemoCalculationViewController *demoVC = [self.storyboard instantiateViewControllerWithIdentifier:@"DemoCalculationViewController"];
    demoVC.popFromDemoBlock = ^(BOOL status) {
        [self initVariable];
        
        [self setUpMemorizeView];
    };
    
    [self.navigationController pushViewController:demoVC animated:YES];
}

#pragma mark - Keyboard handling

- (void)keyboardWillShow:(NSNotification *)notification {
    NSDictionary *userInfo = [notification userInfo];
    
    [UIView animateWithDuration:0.3 animations:^{
        [self.view setFrame:CGRectMake(self.view.frame.origin.x,
                                       self.navigationController.navigationBar.frame.origin.y + self.navigationController.navigationBar.frame.size.height - [[userInfo objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size.height,
                                       self.view.frame.size.width,
                                       self.view.frame.size.height)];
    }];
}

- (void)keyboardWillHide:(NSNotification *)notification {
    [UIView animateWithDuration:0.3 animations:^{
        [self.view setFrame:CGRectMake(self.view.frame.origin.x, 64, self.view.frame.size.width, self.view.frame.size.height)];
    }];
}

#pragma mark - UIView touch event

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.view endEditing:YES];
}

#pragma mark - Random

- (NSMutableArray *)randomNumberWithQuantity:(NSUInteger)quantity rangeStart:(NSUInteger)rangeStart rangeEnd:(NSUInteger)rangeEnd {
    NSMutableArray *resultList = [NSMutableArray arrayWithCapacity:quantity];
    
    for (int i=0; i<quantity; i++) {
        [resultList addObject:@(arc4random_uniform((u_int32_t) rangeStart) + rangeEnd)];
    }
    
    return resultList;
}

#pragma mark - NSTimer handling

- (void)goToNextQuestion {
    self.nextQuestionTimer = nil;
    
    [self setUpMemorizeView];
}

@end
