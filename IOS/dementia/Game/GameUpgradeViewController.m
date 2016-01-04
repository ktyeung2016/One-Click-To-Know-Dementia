//
//  GameUpgradeViewController.m
//  dementia
//
//

#import "GameUpgradeViewController.h"

@interface GameUpgradeViewController ()

@end

@implementation GameUpgradeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:
     [db executeUpdate:@"UPDATE `game_level` SET `level` = ? WHERE `game_type` = ? AND `user_id` = ?",
      [NSString stringWithFormat:@"%lu", (unsigned long)self.difficulty],
      [NSString stringWithFormat:@"%lu", (unsigned long)self.type],
      ([userDefaults objectForKey:@"userInfo"][@"id"] == nil) ? @"0" : [userDefaults objectForKey:@"userInfo"][@"id"]]];
    
    [db close];
}

- (void)setUpLocalizationView {
    [self.congratulationLbl setText:LocalizedString(@"GAME_END_CONGRATULATION")];
    
    NSString *localizationKey = [NSString stringWithFormat:@"GAME_DIFFICULTY_%lu", (unsigned long)self.difficulty];
    [self.remarkLbl setText:[[NSString stringWithFormat:LocalizedString(@"GAME_UPGRADE"), LocalizedString(localizationKey)] stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"]];
    
    [self.confirmBtn setTitle:LocalizedString(@"BUTTON_CONFIRM") forState:UIControlStateNormal];
}

- (void)setUpTextFontSize {
    [self.congratulationLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
    [self.remarkLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    [self.confirmBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

#pragma mark - IBAction

- (IBAction)confirmBtnClick:(id)sender {
    [self dismissViewControllerAnimated:YES completion:^{
        if (self.confirmBlock != nil) {
            self.confirmBlock(true);
        }
    }];
}

@end
