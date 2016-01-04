//
//  GameEndViewController.m
//  dementia
//
//

#import "GameEndViewController.h"

#import "GameListViewController.h"
#import "GameRankingDetailViewController.h"

@interface GameEndViewController ()

@property (assign, nonatomic) NSUInteger ranking;

@end

@implementation GameEndViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self setUpData];
    [self insertIntoDatabase];
}

- (void)setUpLocalizationView {
    [self.congratulationLbl setText:LocalizedString(@"GAME_END_CONGRATULATION")];
    
    [self.questionNumberMark setText:LocalizedString(@"GAME_END_QUESTION_NUMBER_MARK")];
    [self.difficultyMark setText:LocalizedString(@"GAME_END_DIFFICULTY_MARK")];
    [self.scoreMark setText:LocalizedString(@"GAME_END_SCORE_MARK")];
    [self.rankingMark setText:LocalizedString(@"GAME_END_RANKING_MARK")];
    
    [self.shareToFacebookBtn setTitle:[LocalizedString(@"BUTTON_SHARE_FACEBOOK") stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"] forState:UIControlStateNormal];
    [self.shareToEmailBtn setTitle:LocalizedString(@"BUTTON_SHARE_EMAIL") forState:UIControlStateNormal];
    [self.retryBtn setTitle:LocalizedString(@"BUTTON_RETRY") forState:UIControlStateNormal];
    [self.rankingBtn setTitle:LocalizedString(@"BUTTON_RANKING") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.congratulationLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    
    [self.questionNumberMark setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.questionNumberLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.difficultyMark setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.difficultyLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    [self.scoreMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.scoreLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.rankingMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.rankingLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.shareToFacebookBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.shareToEmailBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.retryBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.rankingBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

- (void)setUpData {
    [self.navigationItem setTitle:self.pageTitle];
    
    [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu%@", (unsigned long)self.questionNumber, LocalizedString(@"GAME_END_QUESTION_NUMBER")]];
    
    NSString *localizationKey = [NSString stringWithFormat:@"GAME_DIFFICULTY_%lu", (unsigned long)self.difficulty];
    [self.difficultyLbl setText:LocalizedString(localizationKey)];
    [self.scoreLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.score]];
}

- (void)insertIntoDatabase {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
     [db executeUpdate:@"INSERT INTO `game_score` (`number_of_question`, `difficulty`, `score`, `user_id`, `date`, `game_type`) VALUES (?, ?, ?, ?, ?, ?)",
      [NSString stringWithFormat:@"%lu", (unsigned long)self.questionNumber],
      [NSString stringWithFormat:@"%lu", (unsigned long)self.difficulty],
      @(self.score),
      ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"],
      [NSString stringWithFormat:@"%.0f", [[NSDate date] timeIntervalSince1970]],
      [NSString stringWithFormat:@"%lu", (unsigned long)self.type]]];
    
    NSMutableArray *rankingList = [NSMutableArray array];
    for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_score` WHERE `user_id` = ? AND `game_type` = ? ORDER BY `score` DESC", [userDefaults objectForKey:@"userInfo"][@"id"], [NSString stringWithFormat:@"%lu", (unsigned long)self.type]]
                                    keys:GameScoreTableAttributes]) {
        [rankingList addObject:tempDict[@"score"]];
    }
    
    self.ranking = [rankingList indexOfObject:@(self.score)] + 1;
    
    [self.rankingLbl setText:[NSString stringWithFormat:LocalizedString(@"GAME_END_RANKING"), self.ranking]];
    
    [db close];
    /*
    NSDictionary *tempDict = @{@"game_type":[NSString stringWithFormat:@"%lu", (unsigned long)self.type],
                               @"user_id": [userDefaults objectForKey:@"userInfo"][@"id"],
                               @"marks":[NSString stringWithFormat:@"%lu", (unsigned long)self.score]};
    
    [[JDDataManager sharedInstance] getJSONWithAPI:@"games_insert" parameter:tempDict result:^(NSDictionary *result) {
        
    } errorMsg:^(NSString *errorMsg) {
        
    }];
     */
}

+ (void)submitMark:(int)gameType gameLevel:(unsigned long)gameLevel marks:(unsigned long)marks correctNo:(unsigned long)correctNo {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSDictionary *tempDict = @{@"game_type":[NSString stringWithFormat:@"%d", gameType],
                               @"game_level":[NSString stringWithFormat:@"%lu", gameLevel],
                               @"user_id": [userDefaults objectForKey:@"userInfo"][@"id"],
                               @"marks":[NSString stringWithFormat:@"%lu", marks],
                               @"correct_no":[NSString stringWithFormat:@"%lu", correctNo]};
    
    [[JDDataManager sharedInstance] getJSONWithAPI:@"submit_marks" parameter:tempDict result:^(NSDictionary *result) {
        
    } errorMsg:^(NSString *errorMsg) {
        
    }];
}
#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[GameListViewController class]]) {
            [self.navigationController popToViewController:vc animated:YES];
            break;
        }
    }
}

- (IBAction)shareToFacebookBtnClick:(id)sender {
    SLComposeViewController *socialComposeView = [[SLComposeViewController alloc] init];
    socialComposeView = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];
    
    [socialComposeView setInitialText:[[NSString stringWithFormat:LocalizedString(@"GAME_END_SHARE"), self.pageTitle, self.score] stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"]];
    
    [socialComposeView addImage:[UIImage imageNamed:@"AppIcon"]];
    
    [self presentViewController:socialComposeView animated:YES completion:nil];
    
    [socialComposeView setCompletionHandler:^(SLComposeViewControllerResult result) {
        switch (result) {
            case SLComposeViewControllerResultCancelled:
                break;
                
            case SLComposeViewControllerResultDone:
                break;
                
            default:
                break;
        }
    }];
}

- (IBAction)shareToEmailBtnClick:(id)sender {
    Class mailClass = NSClassFromString(@"MFMailComposeViewController");
    
    if (mailClass != nil && [mailClass canSendMail]) {
        MFMailComposeViewController *mailPicker = [[MFMailComposeViewController alloc] init];
        [mailPicker setMailComposeDelegate:self];
        
        [mailPicker.navigationBar setTintColor:[UIColor whiteColor]];
        
        [mailPicker setToRecipients:nil];
        [mailPicker setCcRecipients:nil];
        [mailPicker setBccRecipients:nil];
        
        [mailPicker setSubject:@""];
        [mailPicker setMessageBody:[[NSString stringWithFormat:LocalizedString(@"GAME_END_SHARE"), self.pageTitle, self.score] stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"] isHTML:NO];
                
        [mailPicker.navigationBar setTranslucent:NO];
        [mailPicker.navigationBar setOpaque:YES];
        
        [self presentViewController:mailPicker animated:YES completion:nil];
    }
}

- (IBAction)retryBtnClick:(id)sender {
    if (self.retryBlock != nil) {
        self.retryBlock(true);
    }
    
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)rankingBtnClick:(id)sender {
    GameRankingDetailViewController *rankingDetailVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameRankingDetailViewController"];
    [rankingDetailVC setDisplayType:@"ThisTime"];
    
    [rankingDetailVC setGameType:[NSString stringWithFormat:@"%lu", (unsigned long)self.type]];
    [rankingDetailVC setQuestionNumber:self.questionNumber];
    [rankingDetailVC setDifficulty:self.difficulty];
    [rankingDetailVC setScore:self.score];
    [rankingDetailVC setRanking:self.ranking];
    
    [self.navigationController pushViewController:rankingDetailVC animated:YES];
}

#pragma mark - MFMail delegate

- (void)mailComposeController:(MFMailComposeViewController*)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error {
    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
