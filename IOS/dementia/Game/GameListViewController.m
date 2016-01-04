//
//  GameListViewController.m
//  dementia
//
//

#import "GameListViewController.h"

#import "GameListCell.h"

@interface GameListViewController ()

@end

@implementation GameListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_GAME")];
    
    [self.resetBtn setTitle:LocalizedString(@"BUTTON_GAME_RESET") forState:UIControlStateNormal];
    [self.rankingBtn setTitle:LocalizedString(@"BUTTON_RANKING") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.resetBtn.titleLabel setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(17.0f)]];
    [self.rankingBtn.titleLabel setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(17.0f)]];
}

#pragma mark - UITableView datasource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 5;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    GameListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"GameListCell" forIndexPath:indexPath];
    
    switch (indexPath.row) {
        case 0:
            [cell setUpViewWithImageName:@"Button_Game_Buyer" gameName:LocalizedString(@"GAME_BUYER")];
            break;
            
        case 1:
            [cell setUpViewWithImageName:@"Button_Game_CutShadow" gameName:LocalizedString(@"GAME_CUT_SHADOW")];
            break;
            
        case 2:
            [cell setUpViewWithImageName:@"Button_Game_FindRoot" gameName:LocalizedString(@"GAME_FIND_ROOT")];
            break;
            
        case 3:
            [cell setUpViewWithImageName:@"Button_Game_Remember" gameName:LocalizedString(@"GAME_REMEMBER")];
            break;
            
        case 4:
            [cell setUpViewWithImageName:@"Button_Game_Calculation" gameName:LocalizedString(@"GAME_CALCULATION")];
            break;
    }
    
    return cell;
}

#pragma mark - UITableView delegate

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return tableView.frame.size.height / 5;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSDictionary *tempDemoDict = [[[JDDataManager sharedInstance] dbHepler] getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `game_demo` WHERE `user_id` = ? AND `game_type` = ?", ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0": [userDefaults objectForKey:@"userInfo"][@"id"], [NSString stringWithFormat:@"%ld", (long)indexPath.row + 1]] keys:GameDemoTableAttributes].firstObject;
    
    [db close];
    
    NSString *identifier;
    
    if ([tempDemoDict[@"shown"] isEqualToString:@"0"]) {
        switch (indexPath.row + 1) {
            case 1:
                identifier = @"DemoBuyerViewController";
                break;
                
            case 2:
                identifier = @"DemoCutShadowViewController";
                break;
                
            case 3:
                identifier = @"DemoFindRootViewController";
                break;
                
            case 4:
                identifier = @"DemoRememberViewController";
                break;
                
            case 5:
                identifier = @"DemoCalculationViewController";
                break;
        }
    }
    else {
        switch (indexPath.row + 1) {
            case 1:
                identifier = @"GameBuyerViewController";
                break;
                
            case 2:
                identifier = @"GameCutShadowViewController";
                break;
                
            case 3:
                identifier = @"GameFindRootViewController";
                break;
                
            case 4:
                identifier = @"GameRememberViewController";
                break;
                
            case 5:
                identifier = @"GameCalculationViewController";
                break;
        }
    }
    
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:identifier] animated:YES];
}

#pragma mark - IBAction

- (IBAction)resetBtnClick:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"GameResetViewController"] animated:YES];
}

- (IBAction)rankingBtnClick:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"GameRankingViewController"] animated:YES];
}

@end
