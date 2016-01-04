//
//  GameRankingDetailViewController.m
//  dementia
//
//

#import "GameRankingDetailViewController.h"

#import "GameRankingCell.h"
#import "GameRankingData.h"

@interface GameRankingDetailViewController ()

@property (strong, nonatomic) NSMutableArray *itemList;

@end

@implementation GameRankingDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [([self.displayType isEqualToString:@"LastTime"]) ? self.thisTimeView : self.lastTimeView setHidden:YES];
    
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.rankingMark setText:LocalizedString(@"BUTTON_RANKING")];
    
    [self.lastFinishMark1 setText:LocalizedString(@"GAME_RANKING_LAST_FINISH")];
    [self.lastFinishMark2 setText:LocalizedString(@"GAME_END_QUESTION_NUMBER")];
    
    /**/
    
    [self.questionNumberMark setText:LocalizedString(@"GAME_END_QUESTION_NUMBER_MARK")];
    [self.difficultyMark setText:LocalizedString(@"GAME_END_DIFFICULTY_MARK")];
    [self.scoreMark setText:LocalizedString(@"GAME_END_SCORE_MARK")];
    [self.thisTimeRankingMark setText:LocalizedString(@"GAME_END_RANKING_MARK")];
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    [self.rankingMark setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];

    [self.lastFinishMark1 setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.lastFinishLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.lastFinishMark2 setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    [self.scoreLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    
    /**/
    
    [self.questionNumberMark setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.questionNumberLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.difficultyMark setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.difficultyLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    [self.scoreMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.scoreLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.rankingMark setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
    [self.rankingLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(19.0f)]];
}

- (void)setUpData {
    self.itemList = [NSMutableArray array];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    NSString *gameTypeLocalizationKey;
    switch ([self.gameType integerValue]) {
        case 1:
            gameTypeLocalizationKey = @"GAME_BUYER";
            break;
            
        case 2:
            gameTypeLocalizationKey = @"GAME_CUT_SHADOW";
            break;
            
        case 3:
            gameTypeLocalizationKey = @"GAME_FIND_ROOT";
            break;
            
        case 4:
            gameTypeLocalizationKey = @"GAME_REMEMBER";
            break;
            
        case 5:
            gameTypeLocalizationKey = @"GAME_CALCULATION";
            break;
    }
    [self.pageTitleLbl setText:LocalizedString(gameTypeLocalizationKey)];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    int i = 0;
    for (NSMutableDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_score` WHERE `user_id` = ? AND `game_type` = ? ORDER BY `score` DESC LIMIT 10", [userDefaults objectForKey:@"userInfo"][@"id"], self.gameType]
                                    keys:GameScoreTableAttributes]) {
        i++;
        
        [tempDict setObject:[NSString stringWithFormat:@"%d", i] forKey:@"ranking"];
        
        GameRankingData *data = [[GameRankingData alloc] initWithData:tempDict];
        [self.itemList addObject:data];
    }
    
    NSDictionary *lastPlayDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_score` WHERE `user_id` = ? AND `game_type` = ? ORDER BY `date` DESC LIMIT 1", [userDefaults objectForKey:@"userInfo"][@"id"], self.gameType] keys:GameScoreTableAttributes].firstObject;
    
    if ([self.displayType isEqualToString:@"LastTime"]) {
        [self.lastFinishLbl setText:lastPlayDict[@"number_of_question"]];
        [self.scoreLbl setText:[NSString stringWithFormat:LocalizedString(@"GAME_RANKING_SCORE"), [lastPlayDict[@"score"] integerValue]]];
    }
    else {
        [self.questionNumberLbl setText:[NSString stringWithFormat:@"%lu%@", (unsigned long)self.questionNumber, LocalizedString(@"GAME_END_QUESTION_NUMBER")]];
        
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DIFFICULTY_%lu", (unsigned long)self.difficulty];
        [self.difficultyLbl setText:LocalizedString(localizationKey)];
        [self.thisTimeScoreLbl setText:[NSString stringWithFormat:@"%lu", (unsigned long)self.score]];
        [self.rankingLbl setText:[NSString stringWithFormat:LocalizedString(@"GAME_END_RANKING"), self.ranking]];
    }
    
    [db close];
}

#pragma mark - UITableView datasource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.itemList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    GameRankingCell *cell = [tableView dequeueReusableCellWithIdentifier:@"GameRankingCell" forIndexPath:indexPath];
    [cell setUpViewWithData:self.itemList[indexPath.row]];
    
    return cell;
}

@end
