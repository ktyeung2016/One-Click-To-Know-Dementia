//
//  GameRankingViewController.m
//  dementia
//
//

#import "GameRankingViewController.h"

#import "GameRankingDetailViewController.h"

@interface GameRankingViewController ()

@end

@implementation GameRankingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_GAME")];
    
    [self.pageTitleLbl setText:LocalizedString(@"BUTTON_RANKING")];
    
    NSArray *gameNameArray = @[@"GAME_BUYER", @"GAME_CUT_SHADOW", @"GAME_FIND_ROOT", @"GAME_REMEMBER", @"GAME_CALCULATION"];
    for (int i=0; i<[gameNameArray count]; i++) {
        [self.gameRankingBtn[i] setTitle:LocalizedString(gameNameArray[i]) forState:UIControlStateNormal];
    }
}

- (void)setUpTextFontSize {
    [self.pageTitleLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    
    for (int i=0; i<[self.gameRankingBtn count]; i++) {
        [((UIButton *) self.gameRankingBtn[i]).titleLabel setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
        [self.scoreLbl[i] setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    }
}

- (void)setUpData {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    for (int i=1; i<6; i++) {
        NSDictionary *tempDict = [[[JDDataManager sharedInstance] dbHepler]
                                   getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_score` WHERE `user_id` = ? AND `game_type` = ? ORDER BY `score` DESC LIMIT 1", [userDefaults objectForKey:@"userInfo"][@"id"], [NSString stringWithFormat:@"%d", i]]
                                  keys:GameScoreTableAttributes].firstObject;
    
        [self.scoreLbl[i - 1] setText:[NSString stringWithFormat:LocalizedString(@"GAME_RANKING_HIGHEST_SCORE"), [tempDict[@"score"] integerValue]]];
        
        if (tempDict == nil) {
            [self.gameRankingBtn[i - 1] setUserInteractionEnabled:NO];
            
        }
    }
    
    [db close];
}

#pragma mark - IBAction

- (IBAction)gameRankingBtnClick:(id)sender {
    NSUInteger gameChose = [self.gameRankingBtn indexOfObject:sender] + 1;
    
    GameRankingDetailViewController *detailVC = [self.storyboard instantiateViewControllerWithIdentifier:@"GameRankingDetailViewController"];
    [detailVC setDisplayType:@"LastTime"];
    [detailVC setGameType:[NSString stringWithFormat:@"%lu", (unsigned long)gameChose]];
    
    [self.navigationController pushViewController:detailVC animated:YES];
}

@end
