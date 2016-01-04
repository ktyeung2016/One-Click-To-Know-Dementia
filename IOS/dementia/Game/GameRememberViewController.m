//
//  GameRememberViewController.m
//  dementia
//
//

#import "GameRememberViewController.h"

#import "GameRememberBoxCell.h"
#import "GameRememberRectangleCell.h"

#import "GameQuestionNumberViewController.h"
#import "GameUpgradeViewController.h"
#import "GameEndViewController.h"

#import "DemoRememberViewController.h"

enum {
    GameModeRemember = 0,
    GameModeSelect = 1
};
typedef NSUInteger GameMode;

@interface GameRememberViewController ()

@property (assign, nonatomic) NSUInteger levelNumber;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger correctNumber;
@property (assign, nonatomic) NSUInteger heartNumber;
@property (assign, nonatomic) NSUInteger totalScore;

@property (strong, nonatomic) NSDictionary *requirementDict;

@property (strong, nonatomic) NSMutableArray *bluePositionList;
@property (strong, nonatomic) NSMutableArray *blackPositionList;

@property (strong, nonatomic) NSMutableArray *selectedList;

@property (assign, nonatomic) GameMode currentMode;

@property (strong, nonatomic) NSTimer *nextQuestionTimer;

@end

@implementation GameRememberViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSDictionary *tempLevelDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ? AND `game_type` = '4'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameLevelTableAttributes].firstObject;
    
    self.levelNumber = (tempLevelDict[@"level"] == nil) ? 1 : [tempLevelDict[@"level"] integerValue];
    
    self.requirementDict = @{@"blue" : @[@4, @5, @7],
                             @"black": @[@0, @1, @2]};
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self initVariable];
    [self setUpData];
    
    NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = '4'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameDemoTableAttributes].firstObject;
    
    if ([tempDemoDict[@"shown"] isEqualToString:@"0"]) {
        [self.questionBtn sendActionsForControlEvents:UIControlEventTouchUpInside];
    }
    
    [db close];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"GAME_REMEMBER")];
    
    [self.pageTitleLbl setText:LocalizedString(@"GAME_REMEMBER_TITLE")];
    
    [self.startAndConfirmBtn setTitle:LocalizedString(@"BUTTON_START") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
}

- (void)initVariable {
    self.questionNumber = 1;
    self.correctNumber = 0;
    self.heartNumber = 5;
    self.totalScore = 100;
    
    for (UIImageView *tempImgView in self.heartImg) {
        [tempImgView setImage:[UIImage imageNamed:@"Game_Heart"]];
    }

    [GameEndViewController submitMark:4 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
}

- (void)setUpData {
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.questionNumber]];
    
    [self.startAndConfirmBtn setTitle:LocalizedString(@"BUTTON_START") forState:UIControlStateNormal];
    
    self.currentMode = GameModeRemember;
    
    if (self.levelNumber != 1) {
        [self.backgroundImgView setImage:[UIImage imageNamed:@"Background_Game_Remember_2"]];
    }
    
    /*
     Hide tick image
     */
    [self.tickImg setHidden:YES];
    
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
     Init
     */
    self.bluePositionList = [NSMutableArray array];
    self.blackPositionList = [NSMutableArray array];
    
    self.selectedList = [NSMutableArray array];
    
    /*
     Random postition
     */
    for (int i=0; i<[self.requirementDict[@"blue"][self.levelNumber - 1] integerValue] + [self.requirementDict[@"black"][self.levelNumber - 1] integerValue]; i++) {
        
        NSNumber *randomNumber = @(arc4random_uniform((self.levelNumber == 1) ? 9 : 12));
        if ([self.bluePositionList containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = @(arc4random_uniform((self.levelNumber == 1) ? 9 : 12));
                
                if (![self.bluePositionList containsObject:newRandomNumber]) {
                    [self.bluePositionList addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [self.bluePositionList addObject:randomNumber];
        }
    }
    
    for (int i=0; i<[self.requirementDict[@"black"][self.levelNumber - 1] integerValue]; i++) {
        NSNumber *randomElement = self.bluePositionList[arc4random_uniform((u_int32_t)[self.bluePositionList count])];
        [self.blackPositionList addObject:randomElement];
        [self.bluePositionList removeObject:randomElement];
    }
    
    [self.boxCollectionView reloadData];
}

- (void)showResultAndNextQuestion:(BOOL)result {
    if (result) {
        [self.tickImg setHidden:NO];
        
        self.correctNumber++;
        
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
        
        if (self.heartNumber > 0) {
            self.questionNumber++;

            if (self.levelNumber < 3 && self.correctNumber % GameQuesionNumberToNext == 0 && result) {
                self.levelNumber++;
                [self showUpgradeView];
            }
            else {
                self.nextQuestionTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(goToNextQuestion) userInfo:nil repeats:NO];
            }
        }
    }
    else {
        self.heartNumber--;
        
        [self.heartImg[self.heartNumber] setImage:[UIImage imageNamed:@"Game_EmptyHeart"]];

        [GameEndViewController submitMark:4 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];

        if (self.heartNumber == 0) {
            GameEndViewController *gameEndVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameEndViewController"];
            [gameEndVC setPageTitle:self.navigationItem.title];
            [gameEndVC setType:4];
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
    [GameEndViewController submitMark:4 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    GameUpgradeViewController *upgradeVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameUpgradeViewController"];
    [upgradeVC setType:4];
    [upgradeVC setDifficulty:self.levelNumber];
    
    upgradeVC.confirmBlock = ^(BOOL status) {
        [self goToNextQuestion];
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

#pragma mark - UICollectionView datasource

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return (self.levelNumber == 1) ? 9 : 12;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    if (self.levelNumber == 1) {
        GameRememberBoxCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameRememberBoxCell" forIndexPath:indexPath];
        
        [cell setUpViewWithBoxShow:(self.currentMode == GameModeRemember) ? [self.bluePositionList containsObject:@(indexPath.row)] : NO];
        
        return cell;
    }
    else {
        GameRememberRectangleCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"GameRememberRectangleCell" forIndexPath:indexPath];
        
        [cell setUpViewWithBlueBoxShow:(self.currentMode == GameModeRemember) ? [self.bluePositionList containsObject:@(indexPath.row)] : NO
                          blackBoxShow:(self.currentMode == GameModeRemember) ? [self.blackPositionList containsObject:@(indexPath.row)] : NO];
        
        return cell;
    }
}

#pragma mark - UICollectionView delegate

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    if (self.currentMode == GameModeSelect) {
        if (self.levelNumber == 1) {
            GameRememberBoxCell *cell = (GameRememberBoxCell *)[collectionView cellForItemAtIndexPath:indexPath];
            [cell updateSelection];
        }
        else {
            GameRememberRectangleCell *cell = (GameRememberRectangleCell *)[collectionView cellForItemAtIndexPath:indexPath];
            [cell updateSelection];
        }
        
        if ([self.selectedList containsObject:@(indexPath.row)]) {
            [self.selectedList removeObject:@(indexPath.row)];
        }
        else {
            [self.selectedList addObject:@(indexPath.row)];
        }
    }
}

#pragma mark - UICollectionViewFlowLayout delegate

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    return CGSizeMake((collectionView.frame.size.width - 34) / 3,
                      (self.levelNumber == 1) ? (collectionView.frame.size.width - 34) / 3 : (collectionView.frame.size.width - 50) / 4);
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    [self.navigationController popToViewController:self.navigationController.viewControllers[3] animated:YES];
}

- (IBAction)startAndConfirmBtnClick:(id)sender {
    if (self.currentMode == GameModeRemember) {
        self.currentMode = GameModeSelect;
        
        [self.startAndConfirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
        
        [self.boxCollectionView reloadData];
    }
    else {
        NSSet *questionSet = [NSSet setWithArray:self.bluePositionList];
        NSSet *answerSet = [NSSet setWithArray:self.selectedList];
        
        if ([questionSet isEqualToSet:answerSet]) {
            [self showResultAndNextQuestion:YES];
            
            self.currentMode = GameModeRemember;
            
            [self.startAndConfirmBtn setTitle:LocalizedString(@"BUTTON_START") forState:UIControlStateNormal];
        }
        else {
            [self showResultAndNextQuestion:NO];
        }
    }
}

- (IBAction)refreshBtnClick:(id)sender {
    if (self.currentMode == GameModeSelect) {
        self.totalScore -= 5;
        
        self.currentMode = GameModeRemember;
        
        [self.startAndConfirmBtn setTitle:LocalizedString(@"BUTTON_START") forState:UIControlStateNormal];
        
        [self.boxCollectionView reloadData];
    }
}

- (IBAction)questionBtnClick:(id)sender {
    DemoRememberViewController *demoVC = [self.storyboard instantiateViewControllerWithIdentifier:@"DemoRememberViewController"];
    demoVC.popFromDemoBlock = ^(BOOL status) {
        [self initVariable];
        
        [self setUpData];
    };
    
    [self.navigationController pushViewController:demoVC animated:YES];
}

#pragma mark - NSTimer handling

- (void)goToNextQuestion {
    self.nextQuestionTimer = nil;
    
    [self setUpData];
}

@end
