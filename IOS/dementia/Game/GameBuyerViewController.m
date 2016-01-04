//
//  GameBuyerViewController.m
//  dementia
//
//

#import "GameBuyerViewController.h"

#import "GameBuyerThingSquareCell.h"
#import "GameBuyerThingRectangleLeftCell.h"
#import "GameBuyerThingRectangleRightCell.h"
#import "GameBuyerThingRectangleFullCell.h"

#import "GameQuestionNumberViewController.h"
#import "GameUpgradeViewController.h"
#import "GameEndViewController.h"

#import "DemoBuyerViewController.h"

#define TotalThingsNumber 30

enum {
    GameStageRemember = 0,
    GameStageSelectDay = 1,
    GameStageSelectThings = 2,
    GameStageTips = 3
};
typedef NSUInteger GameStage;

@interface GameBuyerViewController ()

@property (assign, nonatomic) BOOL isCorrectAnswerPressed;

@property (assign, nonatomic) NSUInteger levelNumber;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger correctNumber;
@property (assign, nonatomic) NSUInteger heartNumber;
@property (assign, nonatomic) NSUInteger totalScore;

@property (assign, nonatomic) NSUInteger sectionNumber;

@property (strong, nonatomic) NSDictionary *requirementDict;

@property (strong, nonatomic) NSArray *questionList;

@property (strong, nonatomic) NSMutableArray *thingsList;
@property (strong, nonatomic) NSMutableArray *choiceList;

@property (assign, nonatomic) GameStage currentStage;

@property (strong, nonatomic) NSTimer *nextSectionTimer;
@property (strong, nonatomic) NSTimer *nextModeTimer;

@end

@implementation GameBuyerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSDictionary *tempLevelDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ? AND `game_type` = '1'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameLevelTableAttributes].firstObject;
    
    self.levelNumber = (tempLevelDict[@"level"] == nil) ? 1 : [tempLevelDict[@"level"] integerValue];
    
    self.requirementDict = @{@"ItemsToMemorize": @[@3, @5, @7],
                             @"ItemsToChoose": @[@4, @6, @9]};
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self initVariable];
    [self setUpData];
    
    NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = '1'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
    
    if ([tempDemoDict[@"shown"] isEqualToString:@"0"]) {
        [self.questionBtn sendActionsForControlEvents:UIControlEventTouchUpInside];
    }
    
    [db close];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"GAME_BUYER")];
    
    [self.tipsTitleLbl setText:LocalizedString(@"GAME_BUYER_TIPS")];
    
    [self.confirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    
    for (UILabel *lbl in self.dayLbl) {
        [lbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    }
    
    [self.tipsTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
    
    [self.confirmBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

- (void)initVariable {
    self.questionNumber = 1;
    self.correctNumber = 0;
    self.heartNumber = 5;
    self.totalScore = 100;
    
    for (UIImageView *tempImgView in self.heartImg) {
        [tempImgView setImage:[UIImage imageNamed:@"Game_Heart"]];
    }
    
    [GameEndViewController submitMark:1 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
}

- (void)setUpPageTitleLbl {
    NSString *localizationKey = [NSString stringWithFormat:@"GAME_BUYER_TITLE_%lu", self.currentStage + 1];
    [self.pageTitleLbl setText:LocalizedString(localizationKey)];
}

- (void)setUpData {
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.questionNumber]];
    
    [self.dayView setHidden:YES];
    
    self.currentStage = GameStageRemember;
    [self setUpPageTitleLbl];
    
    for (UIImageView *imgView in self.starImg) {
        if (self.levelNumber == 1) {
            [imgView setImage:[UIImage imageNamed:@"Game1_EmptyStar"]];
        }
        else {
            [imgView setHidden:YES];
        }
    }
    
    /*
     Init
     */
    self.sectionNumber = 0;
    
    self.thingsList = [NSMutableArray array];
    
    /*
     Random things to memorize
     */
    for (int i=0; i<[self.requirementDict[@"ItemsToMemorize"][self.levelNumber - 1] integerValue]; i++) {
        NSNumber *randomNumber = @(arc4random_uniform(TotalThingsNumber) + 1);
        if ([self.thingsList containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = @(arc4random_uniform(TotalThingsNumber) + 1);
                
                if (![self.thingsList containsObject:newRandomNumber]) {
                    [self.thingsList addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [self.thingsList addObject:randomNumber];
        }
    }
    
    self.questionList = [NSArray arrayWithArray:self.thingsList];
    
    [self.thingsCollectionView reloadData];
    
    self.nextModeTimer = [NSTimer scheduledTimerWithTimeInterval:10 target:self selector:@selector(goToNextMode) userInfo:nil repeats:NO];
}

- (void)setUpDay {
    [self.thingsCollectionView setHidden:YES];
    [self.dayView setHidden:NO];
    
    self.currentStage = GameStageSelectDay;
    [self setUpPageTitleLbl];
    
    /*
     Init
     */
    NSMutableArray *randomDayList = [NSMutableArray array];
    
    /*
     Add random day into list
     */
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *dateComponents = [calendar components:NSCalendarUnitWeekday fromDate:[NSDate date]];
    [randomDayList addObject:@([dateComponents weekday])];
    
    for (int i=0; i<3; i++) {
        NSNumber *randomNumber = @(arc4random_uniform(7) + 1);
        if ([randomDayList containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = @(arc4random_uniform(7) + 1);
                
                if (![randomDayList containsObject:newRandomNumber]) {
                    [randomDayList addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [randomDayList addObject:randomNumber];
        }
    }
    
    /*
     Random day list order
     */
    NSMutableArray *randomArray = [NSMutableArray arrayWithArray:randomDayList];
    NSUInteger count = [randomArray count];
    if (count > 1) {
        for (NSUInteger i=count - 1; i>0; i--) {
            [randomArray exchangeObjectAtIndex:i withObjectAtIndex:arc4random_uniform((int32_t)(i + 1))];
        }
    }
    
    randomDayList = randomArray;
    
    /*
     Set day to view
     */
    for (int i=0; i<[randomDayList count]; i++) {
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DAY_%ld", (long)[randomDayList[i] integerValue]];
        [self.dayLbl[i] setText:[LocalizedString(@"GAME_WEEK") stringByAppendingString:LocalizedString(localizationKey)]];
    }
}

- (void)setUpThingToSelect {
    [self.thingsCollectionView setHidden:NO];
    [self.dayView setHidden:YES];
    
    self.currentStage = GameStageSelectThings;
    [self setUpPageTitleLbl];
    
    self.isCorrectAnswerPressed = false;
    
    /*
     Add things to list
     */
    
    self.choiceList = [NSMutableArray arrayWithObject:self.thingsList[0]];
    
    for (int j=0; j<[self.requirementDict[@"ItemsToChoose"][self.levelNumber - 1] integerValue] - 1; j++) {
        NSNumber *randomNumber = @(arc4random_uniform(TotalThingsNumber) + 1);
        if ([self.choiceList containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = @(arc4random_uniform(TotalThingsNumber) + 1);
                
                if (![self.choiceList containsObject:newRandomNumber]) {
                    [self.choiceList addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [self.choiceList addObject:randomNumber];
        }
    }
    
    NSMutableArray *randomArray = [NSMutableArray arrayWithArray:self.choiceList];
    NSUInteger count = [randomArray count];
    if (count > 1) {
        for (NSUInteger i=count - 1; i>0; i--) {
            [randomArray exchangeObjectAtIndex:i withObjectAtIndex:arc4random_uniform((int32_t)(i + 1))];
        }
    }
    
    self.choiceList = randomArray;
    
    [self.thingsCollectionView reloadData];
}

- (void)showResultAndNextSection:(BOOL)result thingNumber:(NSNumber *)thingNumber {
    if (result) {
        self.isCorrectAnswerPressed = true;
        
        self.sectionNumber++;
        
        [self.thingsList removeObject:thingNumber];
        
        if (self.levelNumber == 1) {
            [self.starImg[self.sectionNumber - 1] setImage:[UIImage imageNamed:@"Game1_Star"]];
        }
        
        self.nextSectionTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(goToNextSection:) userInfo:@{@"result": @(result)} repeats:NO];
    }
    else {
        self.heartNumber--;
        
        [self.heartImg[self.heartNumber] setImage:[UIImage imageNamed:@"Game_EmptyHeart"]];

        [GameEndViewController submitMark:1 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];

        if (self.heartNumber == 0) {
            GameEndViewController *gameEndVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameEndViewController"];
            [gameEndVC setPageTitle:self.navigationItem.title];
            [gameEndVC setType:1];
            [gameEndVC setQuestionNumber:self.correctNumber];
            [gameEndVC setScore:self.totalScore];
            [gameEndVC setDifficulty:self.levelNumber];
            
            gameEndVC.retryBlock = ^(BOOL status) {
                [self initVariable];
                
                [self setUpData];
            };
            
            [self.navigationController pushViewController:gameEndVC animated:YES];
        }
    }
}

- (void)showUpgradeView {
    [GameEndViewController submitMark:1 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    GameUpgradeViewController *upgradeVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameUpgradeViewController"];
    [upgradeVC setType:1];
    [upgradeVC setDifficulty:self.levelNumber];
    
    upgradeVC.confirmBlock = ^(BOOL status) {
        [self setUpData];
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

- (IBAction)dayBtnClick:(id)sender {
    [self setUpThingToSelect];
}

- (IBAction)confirmBtnClick:(id)sender {
    self.currentStage = GameStageSelectThings;
    
    [self.tipsView setHidden:YES];
    
    [self.thingsCollectionView reloadData];
}

- (IBAction)informationBtnClick:(id)sender {
    if (self.currentStage == GameStageSelectThings) {
        self.totalScore -= 5;
        
        self.currentStage = GameStageTips;
        
        [self.tipsView setHidden:NO];
        
        [self.thingsCollectionView reloadData];
    }
}

- (IBAction)questionBtnClick:(id)sender {
    DemoBuyerViewController *demoVC = [self.storyboard instantiateViewControllerWithIdentifier:@"DemoBuyerViewController"];
    demoVC.popFromDemoBlock = ^(BOOL status) {
        [self initVariable];
        
        [self setUpData];
    };
    
    [self.navigationController pushViewController:demoVC animated:YES];
}

#pragma mark - UICollectionView datasource

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    if (self.currentStage == GameStageRemember) {
        return [self.requirementDict[@"ItemsToMemorize"][self.levelNumber - 1] integerValue];
    }
    else if (self.currentStage == GameStageSelectThings) {
        return [self.requirementDict[@"ItemsToChoose"][self.levelNumber - 1] integerValue];
    }
    else if (self.currentStage == GameStageTips) {
        return [self.requirementDict[@"ItemsToMemorize"][self.levelNumber - 1] integerValue];
    }
    
    return 3;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    switch ([collectionView numberOfItemsInSection:0]) {
            /*
             Number of items to memorize
             */
        case 3: {
            GameBuyerThingSquareCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingSquareCell" forIndexPath:indexPath];
            if (self.currentStage == GameStageRemember) {
                [cell setUpViewWithImageNumber:[self.thingsList[indexPath.row] integerValue]];
            }
            else if (self.currentStage == GameStageTips) {
                [cell setUpTipsViewWithImageNumber:[self.questionList[indexPath.row] integerValue]
                                          answered:![self.thingsList containsObject:self.questionList[indexPath.row]]];
            }
            
            return cell;
            
            break;
        }
            
        case 5: {
            if (indexPath.row < 3) {
                GameBuyerThingSquareCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingSquareCell" forIndexPath:indexPath];
                if (self.currentStage == GameStageRemember) {
                    [cell setUpViewWithImageNumber:[self.thingsList[indexPath.row] integerValue]];
                }
                else if (self.currentStage == GameStageTips) {
                    [cell setUpTipsViewWithImageNumber:[self.questionList[indexPath.row] integerValue]
                                              answered:![self.thingsList containsObject:self.questionList[indexPath.row]]];
                }
                
                return cell;
            }
            else {
                if (indexPath.row % 2 != 0) {
                    GameBuyerThingRectangleLeftCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingRectangleLeftCell" forIndexPath:indexPath];
                    if (self.currentStage == GameStageRemember) {
                        [cell setUpViewWithImageNumber:[self.thingsList[indexPath.row] integerValue]];
                    }
                    else if (self.currentStage == GameStageTips) {
                        [cell setUpTipsViewWithImageNumber:[self.questionList[indexPath.row] integerValue]
                                                  answered:![self.thingsList containsObject:self.questionList[indexPath.row]]];
                    }
                    
                    return cell;
                }
                else {
                    GameBuyerThingRectangleRightCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingRectangleRightCell" forIndexPath:indexPath];
                    if (self.currentStage == GameStageRemember) {
                        [cell setUpViewWithImageNumber:[self.thingsList[indexPath.row] integerValue]];
                    }
                    else if (self.currentStage == GameStageTips) {
                        [cell setUpTipsViewWithImageNumber:[self.questionList[indexPath.row] integerValue]
                                                  answered:![self.thingsList containsObject:self.questionList[indexPath.row]]];
                    }
                    
                    return cell;
                }
            }
            
            break;
        }
            
        case 7: {
            if (indexPath.row < 6) {
                GameBuyerThingSquareCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingSquareCell" forIndexPath:indexPath];
                if (self.currentStage == GameStageRemember) {
                    [cell setUpViewWithImageNumber:[self.thingsList[indexPath.row] integerValue]];
                }
                else if (self.currentStage == GameStageTips) {
                    [cell setUpTipsViewWithImageNumber:[self.questionList[indexPath.row] integerValue]
                                              answered:![self.thingsList containsObject:self.questionList[indexPath.row]]];
                }
                
                return cell;
            }
            else {
                GameBuyerThingRectangleFullCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingRectangleFullCell" forIndexPath:indexPath];
                if (self.currentStage == GameStageRemember) {
                    [cell setUpViewWithImageNumber:[self.thingsList[indexPath.row] integerValue]];
                }
                else if (self.currentStage == GameStageTips) {
                    [cell setUpTipsViewWithImageNumber:[self.questionList[indexPath.row] integerValue]
                                              answered:![self.thingsList containsObject:self.questionList[indexPath.row]]];
                }
                
                return cell;
            }
            
            break;
        }
            
            /*
             Number of items on each screen
             */
        case 4: {
            if (indexPath.row % 2 == 0) {
                GameBuyerThingRectangleLeftCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingRectangleLeftCell" forIndexPath:indexPath];
                [cell setUpViewWithImageNumber:[self.choiceList[indexPath.row] integerValue]];
                
                return cell;
            }
            else {
                GameBuyerThingRectangleRightCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingRectangleRightCell" forIndexPath:indexPath];
                [cell setUpViewWithImageNumber:[self.choiceList[indexPath.row] integerValue]];
                
                return cell;
            }
            
            break;
        }
            
        case 6:
        case 9: {
            GameBuyerThingSquareCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameBuyerThingSquareCell" forIndexPath:indexPath];
            [cell setUpViewWithImageNumber:[self.choiceList[indexPath.row] integerValue]];
            
            return cell;
            
            break;
        }
    }
    
    return nil;
}

#pragma mark - UICollectionView delegate

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    if (self.currentStage == GameStageSelectThings) {
        
        if (!self.isCorrectAnswerPressed) {
            if ([[collectionView cellForItemAtIndexPath:indexPath] isKindOfClass:[GameBuyerThingSquareCell class]]) {
                
                GameBuyerThingSquareCell *cell = (GameBuyerThingSquareCell *)[collectionView cellForItemAtIndexPath:indexPath];
                
                [cell updateOutlineImage:[self.thingsList containsObject:@(cell.thingImgView.tag)]];
                
                [self showResultAndNextSection:[self.thingsList containsObject:@(cell.thingImgView.tag)]
                                   thingNumber:@(cell.thingImgView.tag)];
            }
            else if ([[collectionView cellForItemAtIndexPath:indexPath] isKindOfClass:[GameBuyerThingRectangleLeftCell class]]) {
                
                GameBuyerThingRectangleLeftCell *cell = (GameBuyerThingRectangleLeftCell *)[collectionView cellForItemAtIndexPath:indexPath];
                
                [cell updateOutlineImage:[self.thingsList containsObject:@(cell.thingImgView.tag)]];
                
                [self showResultAndNextSection:[self.thingsList containsObject:@(cell.thingImgView.tag)]
                                   thingNumber:@(cell.thingImgView.tag)];
            }
            else if ([[collectionView cellForItemAtIndexPath:indexPath] isKindOfClass:[GameBuyerThingRectangleRightCell class]]) {
                
                GameBuyerThingRectangleRightCell *cell = (GameBuyerThingRectangleRightCell *)[collectionView cellForItemAtIndexPath:indexPath];
                
                [cell updateOutlineImage:[self.thingsList containsObject:@(cell.thingImgView.tag)]];
                
                [self showResultAndNextSection:[self.thingsList containsObject:@(cell.thingImgView.tag)]
                                   thingNumber:@(cell.thingImgView.tag)];
            }
        }
    }
}

#pragma mark - UICollectionViewFlowLayout delegate

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    switch ([collectionView numberOfItemsInSection:0]) {
            /*
             Number of items to memorize
             */
        case 3:
            [self.thingsCollectionViewHeightConstraint setConstant:97];
            
            return CGSizeMake(96, 97);
            
            break;
            
        case 5:
            [self.thingsCollectionViewHeightConstraint setConstant:204];
            
            if (indexPath.row < 3) {
                return CGSizeMake(96, 97);
            }
            else {
                return CGSizeMake(152, 97);
            }
            
            break;
            
        case 7:
            [self.thingsCollectionViewHeightConstraint setConstant:311];
            
            if (indexPath.row < 6) {
                return CGSizeMake(96, 97);
            }
            else {
                return CGSizeMake(312, 97);
            }
            
            break;
            
            /*
             Number of items on each screen
             */
        case 4:
            [self.thingsCollectionViewHeightConstraint setConstant:204];
            
            return CGSizeMake(152, 97);
            
            break;
            
        case 6:
            [self.thingsCollectionViewHeightConstraint setConstant:204];
            
            return CGSizeMake(96, 97);
            
            break;
            
        case 9:
            [self.thingsCollectionViewHeightConstraint setConstant:311];
            
            return CGSizeMake(96, 97);
            
            break;
    }
    
    return CGSizeMake(96, 97);
}

#pragma mark - NSTimer handling

- (void)goToNextMode {
    self.nextModeTimer = nil;
    
    [self setUpDay];
}

- (void)goToNextSection:(NSTimer *)timer {
    self.nextSectionTimer = nil;
    
    NSDictionary *timerInfo = [timer userInfo];
    
    if (self.heartNumber > 0) {
        if (self.sectionNumber == [self.questionList count]) {
            self.correctNumber++;
            
            self.questionNumber++;
            
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
            
            if (self.levelNumber < 3 && self.correctNumber % GameQuesionNumberToNext == 0 && [timerInfo[@"result"] boolValue]) {
                self.levelNumber++;
                
                [self showUpgradeView];
            }
            else {
                [self setUpData];
            }
        }
        else {
            [self setUpThingToSelect];
            
            [self.thingsCollectionView reloadData];
        }
    }
}

@end
