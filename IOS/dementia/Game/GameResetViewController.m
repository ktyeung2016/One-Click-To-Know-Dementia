//
//  GameResetViewController.m
//  dementia
//
//

#import "GameResetViewController.h"

@interface GameResetViewController ()

@end

@implementation GameResetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_GAME")];
    
    NSArray *gameNameArray = @[@"GAME_BUYER", @"GAME_CUT_SHADOW", @"GAME_FIND_ROOT", @"GAME_REMEMBER", @"GAME_CALCULATION"];
    for (int i=0; i<[gameNameArray count]; i++) {
        [self.gameNameLbl[i] setText:LocalizedString(gameNameArray[i])];
        [self.difficultyMark[i] setText:LocalizedString(@"GAME_END_DIFFICULTY_MARK")];
        [self.resetBtn[i] setTitle:LocalizedString(@"BUTTON_GAME_RESET") forState:UIControlStateNormal];
    }
}

- (void)setUpTextFontSize {
    for (int i=0; i<[self.gameNameLbl count]; i++) {
        [self.gameNameLbl[i] setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(21.0f)]];
        [self.difficultyMark[i] setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        [self.difficultyLbl[i] setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        [((UIButton *) self.resetBtn[i]).titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    }
}

- (void)setUpData {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_level` WHERE `user_id` = ?", [userDefaults objectForKey:@"userInfo"][@"id"]]
                                    keys:GameLevelTableAttributes]) {
        
        NSString *localizationKey = [NSString stringWithFormat:@"GAME_DIFFICULTY_%@", tempDict[@"level"]];
        [self.difficultyLbl[[tempDict[@"game_type"] integerValue] - 1] setText:LocalizedString(localizationKey)];
        
        [self.resetBtn[[tempDict[@"game_type"] integerValue] - 1] setTag:[tempDict[@"id"] integerValue]];
    }
    
    [db close];
}

#pragma mark - IBAction

- (IBAction)resetBtnClick:(id)sender {
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    if ([[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"UPDATE `game_level` SET `level` = '1' WHERE `id` = ?", [NSString stringWithFormat:@"%ld", (long)[sender tag]]]]) {
        
        [self setUpData];
    }
    
    [db close];
}

@end
