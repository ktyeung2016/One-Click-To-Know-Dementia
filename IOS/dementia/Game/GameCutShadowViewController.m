//
//  GameCutShadowViewController.m
//  dementia
//
//

#import "GameCutShadowViewController.h"

#import "GameQuestionNumberViewController.h"
#import "GameUpgradeViewController.h"
#import "GameEndViewController.h"

#import "DemoCutShadowViewController.h"

@interface GameCutShadowViewController ()

@property (assign, nonatomic) BOOL isViewLoaded;

@property (assign, nonatomic) NSUInteger levelNumber;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger correctNumber;
@property (assign, nonatomic) NSUInteger heartNumber;
@property (assign, nonatomic) NSUInteger totalScore;

@property (strong, nonatomic) NSArray *allQuestionArray;
@property (strong, nonatomic) NSArray *numberOfAnswerArray;

@property (strong, nonatomic) NSMutableArray *questionNumberArray;
@property (strong, nonatomic) NSMutableArray *questionImageArray;
@property (strong, nonatomic) NSMutableArray *answerImageArray;

@property (strong, nonatomic) NSMutableArray *tipsArray;

@property (strong, nonatomic) NSMutableDictionary *originalRectDict;

@property (strong, nonatomic) NSTimer *nextQuestionTimer;

@end

@implementation GameCutShadowViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSDictionary *tempDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ? AND `game_type` = '2'", [userDefaults objectForKey:@"userInfo"][@"id"]] keys:GameLevelTableAttributes].firstObject;
    
    [db close];
    
    self.levelNumber = (tempDict[@"level"] == nil) ? 1 : [tempDict[@"level"] integerValue];
    
    self.allQuestionArray = @[@2, @5, @8, @9, @3, @6, @1, @4, @7];
    self.numberOfAnswerArray = @[@4, @6, @9];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self initVariable];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    if (!self.isViewLoaded) {
        [self setUpData];
    }
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"GAME_CUT_SHADOW")];
    
    [self.pageTitleLbl setText:LocalizedString(@"GAME_CUT_SHADOW_TITLE")];
    [self.confirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
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
    
    [GameEndViewController submitMark:2 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
}

- (void)setUpData {
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.questionNumber]];
    
    for (UIView *view in self.shadowView.subviews) {
        if ([view tag] != 1 && [view tag] != 2) {
            [view removeFromSuperview];
        }
    }
    
    for (UIView *view in self.scrollPuzzle.subviews) {
        [view removeFromSuperview];
    }
    
    [self.confirmBtn setHidden:YES];
    
    [self.scrollPuzzle setHidden:NO];
    
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
    self.questionImageArray = [NSMutableArray array];
    self.answerImageArray = [NSMutableArray array];
    
    self.tipsArray = [NSMutableArray array];
    
    self.originalRectDict = [NSMutableDictionary dictionary];
    
    /*
     Random question & answer
     */
    for (int i=0; i<3; i++) {
        NSNumber *randomNumber = self.allQuestionArray[arc4random_uniform([self.numberOfAnswerArray[self.levelNumber - 1] intValue])];
        if ([self.questionImageArray containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = self.allQuestionArray[arc4random_uniform([self.numberOfAnswerArray[self.levelNumber - 1] intValue])];
                
                if (![self.questionImageArray containsObject:newRandomNumber]) {
                    [self.questionImageArray addObject:newRandomNumber];
                    [self.answerImageArray addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [self.questionImageArray addObject:randomNumber];
            [self.answerImageArray addObject:randomNumber];
        }
    }
    
    for (int i=0; i<[self.numberOfAnswerArray[self.levelNumber - 1] integerValue] - 3; i++) {
        NSNumber *randomNumber = self.allQuestionArray[arc4random_uniform([self.numberOfAnswerArray[self.levelNumber - 1] intValue])];
        if ([self.answerImageArray containsObject:randomNumber]) {
            NSNumber *newRandomNumber = @0;
            while (newRandomNumber) {
                newRandomNumber = self.allQuestionArray[arc4random_uniform([self.numberOfAnswerArray[self.levelNumber - 1] intValue])];
                
                if (![self.answerImageArray containsObject:newRandomNumber]) {
                    [self.answerImageArray addObject:newRandomNumber];
                    
                    break;
                }
            }
        }
        else {
            [self.answerImageArray addObject:randomNumber];
        }
    }
    
    /*
     Convert component in array to image
     */
    self.questionNumberArray = [NSMutableArray arrayWithArray:self.questionImageArray];
    
    for (int i=0; i<[self.questionImageArray count]; i++) {
        UIImage *image = [UIImage imageNamed:[NSString stringWithFormat:@"CutShadow00%@", self.questionImageArray[i]]];
        [image setAccessibilityIdentifier:self.questionImageArray[i]];
        [self.questionImageArray replaceObjectAtIndex:i withObject:image];
    }
    
    for (int i=0; i<[self.answerImageArray count]; i++) {
        UIImage *image = [UIImage imageNamed:[NSString stringWithFormat:@"CutShadow00%@_shadow", self.answerImageArray[i]]];
        [image setAccessibilityIdentifier:self.answerImageArray[i]];
        [self.answerImageArray replaceObjectAtIndex:i withObject:image];
    }
    
    /*
     Random answer list order
     */
    NSMutableArray *randomArray = [NSMutableArray arrayWithArray:self.answerImageArray];
    NSUInteger count = [randomArray count];
    if (count > 1) {
        for (NSUInteger i=count - 1; i>0; i--) {
            [randomArray exchangeObjectAtIndex:i withObjectAtIndex:arc4random_uniform((int32_t)(i + 1))];
        }
    }
    
    self.answerImageArray = randomArray;
    
    /*
     Add image to view
     */
    for (UIImage *image in self.questionImageArray) {
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:
                                  CGRectMake(arc4random_uniform(self.shadowView.frame.size.width - image.size.width - 30) + 30,
                                             arc4random_uniform(self.shadowView.frame.size.height - image.size.height - 30) + 30,
                                             image.size.width,
                                             image.size.height)];
        [imageView setImage:image];
        [imageView setAccessibilityIdentifier:[image accessibilityIdentifier]];
        
        [self.shadowView addSubview:imageView];
    }
    
    for (int i=0; i<[self.answerImageArray count]; i++) {
        UIImage *image = self.answerImageArray[i];
        
        CGFloat pointX = (i == 0) ? 10 : ((UIImageView *) self.scrollPuzzle.subviews.lastObject).frame.origin.x + ((UIImageView *) self.scrollPuzzle.subviews.lastObject).frame.size.width + 10;
        
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:
                                  CGRectMake(pointX,
                                             0,
                                             image.size.width,
                                             image.size.height)];
        [imageView setCenter:CGPointMake(imageView.center.x, self.scrollPuzzle.frame.size.height / 2)];
        [imageView setUserInteractionEnabled:YES];
        [imageView setImage:image];
        [imageView setAccessibilityIdentifier:[image accessibilityIdentifier]];
        
        UIPanGestureRecognizer *panGesture = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(panGestureHandler:)];
        [panGesture setDelegate:self];
        [imageView addGestureRecognizer:panGesture];
        
        [self.scrollPuzzle addSubview:imageView];
        
        if (i == [self.answerImageArray count] - 1) {
            [self.scrollPuzzle setContentSize:CGSizeMake(imageView.frame.origin.x + imageView.frame.size.width + 10,
                                                         self.scrollPuzzle.frame.size.height)];
        }
    }
    
    [self.scrollPuzzle setContentOffset:CGPointZero];
    
    self.isViewLoaded = true;
}

- (void)showResultAndNextQuestion:(BOOL)result {
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
        
        [GameEndViewController submitMark:2 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    }
    
    [self.shadowView bringSubviewToFront:self.tickImg];
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
        [gameEndVC setType:2];
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
    [GameEndViewController submitMark:2 gameLevel:self.levelNumber marks:self.totalScore correctNo:self.correctNumber];
    GameUpgradeViewController *upgradeVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameUpgradeViewController"];
    [upgradeVC setType:2];
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

- (IBAction)confirmBtnClick:(id)sender {
    NSMutableArray *tempArray = [NSMutableArray array];
    
    for (NSNumber *number in self.questionNumberArray) {
        for (UIImageView *imageView in self.shadowView.subviews) {
            if ([number isEqual:[imageView accessibilityIdentifier]]) {
                [tempArray addObject:imageView];
            }
        }
        
        [tempArray addObject:@"|"];
    }
    
    if ([tempArray count] == 9) {
        UIImageView *questionImageView;
        UIImageView *answerImageView;
        
        NSUInteger correctImageNumber = 0;
        
        for (int i=0; i<[tempArray count]; i++) {
            if ([tempArray[i] isKindOfClass:[UIImageView class]] && questionImageView == nil) {
                questionImageView = tempArray[i];
            }
            else if ([tempArray[i] isKindOfClass:[UIImageView class]] && questionImageView != nil) {
                answerImageView = tempArray[i];
            }
            
            if (questionImageView != nil && answerImageView != nil) {
                int distance = (int)sqrt(pow((questionImageView.frame.origin.x - answerImageView.frame.origin.x), 2) + pow((questionImageView.frame.origin.y - answerImageView.frame.origin.y), 2));
                
                if (distance < 20) {
                    correctImageNumber++;
                }
                
                questionImageView = nil;
                answerImageView = nil;
            }
        }
        
        if (correctImageNumber == 3) {
            [self showResultAndNextQuestion:YES];
        }
        else {
            [self showResultAndNextQuestion:NO];
        }
    }
    else {
        [self showResultAndNextQuestion:NO];
    }
}

- (IBAction)informationBtnClick:(id)sender {
    self.totalScore -= 5;
    
    BOOL isTipsOn = false;
    
    for (UIImageView *imgView in self.scrollPuzzle.subviews) {
        if ([self.tipsArray containsObject:imgView.accessibilityIdentifier]) {
            isTipsOn = true;

            break;
        }
    }
    
    if (!isTipsOn) {
        for (UIImageView *questionImgView in self.shadowView.subviews) {
            NSString *tempAccessibilityIdentifier = questionImgView.accessibilityIdentifier;
            
            if ([questionImgView isKindOfClass:[UIImageView class]] && [questionImgView tag] != 1 && [questionImgView tag] != 2 && ![self.tipsArray containsObject:tempAccessibilityIdentifier]) {
                
                [questionImgView setImage:[self addLightFilterToImage:questionImgView.image]];
                
                for (UIImageView *answerImgView in self.scrollPuzzle.subviews) {
                    if ([answerImgView isKindOfClass:[UIImageView class]] &&
                        [answerImgView.accessibilityIdentifier isEqual:tempAccessibilityIdentifier]) {
                        
                        [answerImgView setImage:[self addLightFilterToImage:answerImgView.image]];
                        
                        [self.tipsArray addObject:tempAccessibilityIdentifier];
                        
                        break;
                    }
                }
                
                break;
            }
        }
    }
}

- (IBAction)questionBtnClick:(id)sender {
    DemoCutShadowViewController *demoVC = [self.storyboard instantiateViewControllerWithIdentifier:@"DemoCutShadowViewController"];
    demoVC.popFromDemoBlock = ^(BOOL status) {
        [self initVariable];
        
        [self setUpData];
    };
    
    [self.navigationController pushViewController:demoVC animated:YES];
}

#pragma mark - UIGestureRecongnizer handling

- (void)panGestureHandler:(UIPanGestureRecognizer *)gestureRecognizer {
    
    if (gestureRecognizer.state == UIGestureRecognizerStateBegan) {
        if ([self.scrollPuzzle.subviews containsObject:gestureRecognizer.view]) {
            
            [self.originalRectDict setObject:NSStringFromCGRect(gestureRecognizer.view.frame) forKey:gestureRecognizer.view.accessibilityIdentifier];
            
            UIImageView *newImageView = ((UIImageView *) gestureRecognizer.view);
            [newImageView setFrame:CGRectMake(gestureRecognizer.view.frame.origin.x - self.scrollPuzzle.contentOffset.x,
                                              self.scrollPuzzle.frame.origin.y + gestureRecognizer.view.frame.origin.y,
                                              newImageView.frame.size.width,
                                              newImageView.frame.size.height)];
            
            [self.view addSubview:newImageView];
        }
        else if ([self.shadowView.subviews containsObject:gestureRecognizer.view]) {
            UIImageView *newImageView = ((UIImageView *) gestureRecognizer.view);
            [newImageView setFrame:CGRectMake(gestureRecognizer.view.frame.origin.x,
                                              self.shadowView.frame.origin.y + gestureRecognizer.view.frame.origin.y,
                                              newImageView.frame.size.width,
                                              newImageView.frame.size.height)];
            
            [self.view addSubview:newImageView];
        }
    }
    else if (gestureRecognizer.state == UIGestureRecognizerStateEnded) {
        if (gestureRecognizer.view.frame.origin.y > self.shadowView.frame.origin.y &&
            gestureRecognizer.view.frame.origin.y + gestureRecognizer.view.frame.size.height < self.shadowView.frame.origin.y + self.shadowView.frame.size.height &&
            [self.view.subviews containsObject:gestureRecognizer.view]) {
            
            UIImageView *newImageView = ((UIImageView *) gestureRecognizer.view);
            [newImageView setFrame:CGRectMake(gestureRecognizer.view.frame.origin.x,
                                              gestureRecognizer.view.frame.origin.y - self.shadowView.frame.origin.y,
                                              newImageView.frame.size.width,
                                              newImageView.frame.size.height)];
            
            [self.shadowView addSubview:newImageView];
            
            if (self.scrollPuzzle.subviews.count == [self.numberOfAnswerArray[self.levelNumber - 1] integerValue] - 3) {
                [self.scrollPuzzle setHidden:YES];
                [self.confirmBtn setHidden:NO];
                
                for (UIImageView *imgView in self.shadowView.subviews) {
                    if ([imgView isKindOfClass:[UIImageView class]] && [imgView tag] != 1 && [imgView tag] != 2) {
                        [imgView setUserInteractionEnabled:NO];
                    }
                }
            }
        }
        else if (gestureRecognizer.view.frame.origin.y > self.scrollPuzzle.frame.origin.y &&
                 gestureRecognizer.view.frame.origin.y + gestureRecognizer.view.frame.size.height < self.scrollPuzzle.frame.origin.y + self.scrollPuzzle.frame.size.height &&
                 [self.view.subviews containsObject:gestureRecognizer.view]) {
            
            UIImageView *newImageView = ((UIImageView *) gestureRecognizer.view);
            [newImageView setFrame:CGRectFromString(self.originalRectDict[gestureRecognizer.view.accessibilityIdentifier])];
            
            [self.scrollPuzzle addSubview:newImageView];
        }
        else {
            UIImageView *newImageView = ((UIImageView *) gestureRecognizer.view);
            [newImageView setFrame:CGRectFromString(self.originalRectDict[gestureRecognizer.view.accessibilityIdentifier])];
            
            [self.scrollPuzzle addSubview:newImageView];
            
            [self.view makeToast:LocalizedString(@"ALERT_CANT_DROP_HERE")];
        }
    }
    
    CGPoint translation = [gestureRecognizer translationInView:self.view];
    gestureRecognizer.view.center = CGPointMake(gestureRecognizer.view.center.x + translation.x,
                                                gestureRecognizer.view.center.y + translation.y);
    [gestureRecognizer setTranslation:CGPointMake(0, 0) inView:self.view];
}

#pragma mark - NSTimer handling

- (void)goToNextQuestion {
    self.nextQuestionTimer = nil;
    
    [self setUpData];
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
