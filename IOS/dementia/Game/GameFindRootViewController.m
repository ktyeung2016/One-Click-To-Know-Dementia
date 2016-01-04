//
//  GameFindRootViewController.m
//  dementia
//
//

#import "GameFindRootViewController.h"

#import "GameQuestionNumberViewController.h"
#import "GameUpgradeViewController.h"
#import "GameEndViewController.h"

#import "DemoFindRootViewController.h"

#define TotalElementNumbers 243

@interface GameFindRootViewController ()

@property (assign, nonatomic) NSUInteger levelNumber;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger correctNumber;
@property (assign, nonatomic) NSUInteger heartNumber;
@property (assign, nonatomic) NSUInteger totalScore;

@property (assign, nonatomic) NSUInteger answerNumber;
@property (assign, nonatomic) NSUInteger elementNumber;

@property (strong, nonatomic) NSString *questionImageName;
@property (strong, nonatomic) NSString *answerImageName;

@property (strong, nonatomic) NSMutableArray *imageNameArray;
@property (strong, nonatomic) NSMutableArray *elementArray;

@property (strong, nonatomic) NSTimer *nextQuestionTimer;

@end

@implementation GameFindRootViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSDictionary *tempLevelDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ? AND `game_type` = '3'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameLevelTableAttributes].firstObject;
    
    [db close];
    
    self.levelNumber = (tempLevelDict[@"level"] == nil) ? 1 : [tempLevelDict[@"level"] integerValue];
    
    self.imageNameArray = [NSMutableArray array];
    for (int i=0; i<6; i++) {
        [self.imageNameArray addObject:[NSMutableArray array]];
    }
    
    for (UIButton *tempBtn in self.answerBtn) {
        [tempBtn.imageView setContentMode:UIViewContentModeScaleAspectFit];
    }
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self prepareAllImage];
    [self initVariable];
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"GAME_FIND_ROOT")];
    
    [self.pageTitleLbl setText:LocalizedString(@"GAME_FIND_ROOT_TITLE")];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
}

- (void)prepareAllImage {
    NSMutableArray *gameImageArray = [NSMutableArray array];
    
    NSArray *allImageArray = [[NSBundle mainBundle] pathsForResourcesOfType:@"png" inDirectory:nil];
    
    for (NSString *pathString in allImageArray) {
        NSString *tempString = [pathString stringByReplacingOccurrencesOfString:[NSString stringWithFormat:@"%@/", [[NSBundle mainBundle] resourcePath]] withString:@""];
        
        if ([tempString hasPrefix:@"game3_lv"]) {
            [gameImageArray addObject:[tempString stringByReplacingOccurrencesOfString:@".png" withString:@""]];
        }
    }
    
    for (NSString *imageName in gameImageArray) {
        if ([imageName rangeOfString:@"_lv1_q"].location != NSNotFound) {
            [self.imageNameArray[0] addObject:imageName];
        }
        else if ([imageName rangeOfString:@"_lv2_q"].location != NSNotFound) {
            [self.imageNameArray[1] addObject:imageName];
        }
        else if ([imageName rangeOfString:@"_lv3_q"].location != NSNotFound) {
            [self.imageNameArray[2] addObject:imageName];
        }
        else if ([imageName rangeOfString:@"_lv1_a"].location != NSNotFound) {
            [self.imageNameArray[3] addObject:imageName];
        }
        else if ([imageName rangeOfString:@"_lv2_a"].location != NSNotFound) {
            [self.imageNameArray[4] addObject:imageName];
        }
        else if ([imageName rangeOfString:@"_lv3_a"].location != NSNotFound) {
            [self.imageNameArray[5] addObject:imageName];
        }
    }
}

- (void)initVariable {
    self.questionNumber = 1;
    self.correctNumber = 0;
    self.heartNumber = 5;
    self.totalScore = 100;
    
    for (UIImageView *tempImgView in self.heartImg) {
        [tempImgView setImage:[UIImage imageNamed:@"Game_Heart"]];
    }
    
    [GameEndViewController submitMark:3 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
}

- (void)setUpData {
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.questionNumber]];
    
    for (UIButton *tempBtn in self.answerBtn) {
        [tempBtn setHidden:NO];
    }
    
    [self.informationBtn setUserInteractionEnabled:YES];
    
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
    self.elementArray = [NSMutableArray array];
    
    /*
     Random question
     */
    self.questionImageName = self.imageNameArray[self.levelNumber - 1][arc4random_uniform((u_int32_t)[self.imageNameArray[self.levelNumber - 1] count])];
    self.answerImageName = [self.questionImageName stringByReplacingOccurrencesOfString:@"_q" withString:@"_a"];
    
    self.elementNumber = [[self.questionImageName componentsSeparatedByString:@"_e"][1] integerValue];
    
    [self.elementArray addObject:@(self.elementNumber)];
    
    for (int i=0; i<2; i++) {
        NSNumber *randomNumber = @(arc4random_uniform(TotalElementNumbers) + 1);
        if ([self.elementArray containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = @(arc4random_uniform(TotalElementNumbers) + 1);
                
                if (![self.elementArray containsObject:newRandomNumber]) {
                    [self.elementArray addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [self.elementArray addObject:randomNumber];
        }
    }
    
    NSMutableArray *mutableArray = [NSMutableArray arrayWithArray:self.elementArray];
    NSUInteger count = [mutableArray count];
    if (count > 1) {
        for (NSUInteger i = count - 1; i > 0; --i) {
            [mutableArray exchangeObjectAtIndex:i withObjectAtIndex:arc4random_uniform((int32_t)(i + 1))];
        }
    }
    
    NSArray *randomArray = [NSArray arrayWithArray:mutableArray];
    
    for (int i=0; i<[randomArray count]; i++) {
        NSString *imageName;
        
        if ([randomArray[i] integerValue] < 10) {
            imageName = [NSString stringWithFormat:@"game3_e00%@", randomArray[i]];
        }
        else if ([randomArray[i] integerValue] < 100) {
            imageName = [NSString stringWithFormat:@"game3_e0%@", randomArray[i]];
        }
        else {;
            imageName = [NSString stringWithFormat:@"game3_e%@", randomArray[i]];
        }
        
        [self.answerBtn[i] setImage:[UIImage imageNamed:imageName] forState:UIControlStateNormal];
        [self.answerBtn[i] setTag:[randomArray[i] integerValue]];
    }
    
    [self.questionImgView setImage:[UIImage imageNamed:self.questionImageName]];
}

- (void)showResultAndNextQuestion:(BOOL)result {
    [self.questionImgView setImage:[UIImage imageNamed:self.answerImageName]];
    
    if (result) {
        [self.tickImg setImage:[UIImage imageNamed:@"Tick"]];
        
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
    }
    else {
        [self.tickImg setImage:[UIImage imageNamed:@"Cross"]];
        
        self.heartNumber--;
        
        [self.heartImg[self.heartNumber] setImage:[UIImage imageNamed:@"Game_EmptyHeart"]];
        
        [GameEndViewController submitMark:3 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    }
    
    [self.tickImg setHidden:NO];
    
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
    else {
        GameEndViewController *gameEndVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameEndViewController"];
        [gameEndVC setPageTitle:self.navigationItem.title];
        [gameEndVC setType:3];
        [gameEndVC setQuestionNumber:self.questionNumber];
        [gameEndVC setScore:self.totalScore];
        [gameEndVC setDifficulty:self.levelNumber];
        
        gameEndVC.retryBlock = ^(BOOL status) {
            [self initVariable];
            
            [self setUpData];
        };
        
        [self.navigationController pushViewController:gameEndVC animated:YES];
    }
}

- (void)showUpgradeView {
    [GameEndViewController submitMark:3 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    GameUpgradeViewController *upgradeVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameUpgradeViewController"];
    [upgradeVC setType:3];
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

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    [self.navigationController popToViewController:self.navigationController.viewControllers[3] animated:YES];
}

- (IBAction)answerBtnClick:(id)sender {
    [self showResultAndNextQuestion:([sender tag] == self.elementNumber) ? YES : NO];
}
 
- (IBAction)informationBtnClick:(id)sender {
    NSMutableArray *tempList = self.elementArray;
    [tempList removeObject:@(self.elementNumber)];
    
    NSUInteger elementNumToHidden = [tempList[arc4random_uniform(2)] integerValue];
    for (UIButton *tempBtn in self.answerBtn) {
        if (tempBtn.tag == elementNumToHidden) {
            [tempBtn setHidden:YES];
            
            break;
        }
    }
    
    self.totalScore -= 5;
    
    [self.informationBtn setUserInteractionEnabled:NO];
}

- (IBAction)questionBtnClick:(id)sender {
    DemoFindRootViewController *demoVC = [self.storyboard instantiateViewControllerWithIdentifier:@"DemoFindRootViewController"];
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
