//
//  MainViewController.m
//  dementia
//
//

// MAIN_LOADING

#import "MainViewController.h"

#import "ECSlidingViewController.h"

@interface MainViewController ()

@property (strong, nonatomic) NSTimer *tipsTimer;
@property (strong, nonatomic) NSTimer *progressTimer;

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self.navigationController setNavigationBarHidden:YES];
    
    [self setUpTextFontSize];
    
    [self.tipsLbl setText:[self randomTips]];
    
    self.tipsTimer = [NSTimer scheduledTimerWithTimeInterval:5 target:self selector:@selector(tipsStringChange) userInfo:nil repeats:YES];
    
    [[JDDataManager sharedInstance] getJSONWithAPI:@"auth" parameter:nil result:^(NSDictionary *result) {
        if (result != nil) {
            [[JDDataManager sharedInstance] setAccessToken:result[@"token"]];
            
            [self downloadDataFromServer];
            
            [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"LoginViewController"] animated:YES];
        }
    } errorMsg:^(NSString *errorMsg) {
        [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"LoginViewController"] animated:YES];
    }];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    if (self.tipsTimer == NULL) {
        self.tipsTimer = [NSTimer scheduledTimerWithTimeInterval:5 target:self selector:@selector(tipsStringChange) userInfo:nil repeats:YES];
    }
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    
    [self.tipsTimer invalidate];
    self.tipsTimer = NULL;
}

- (void)setUpTextFontSize {
    [self.tipsLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(21.0f)]];
    [self.progressLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
}

- (NSString *)randomTips {
    NSString *localzationKey = [NSString stringWithFormat:@"TIPS_%d", (arc4random() % 141) + 1];
    NSArray *splitString = [LocalizedString(localzationKey) componentsSeparatedByString:@"\\n"];
    return [NSString stringWithFormat:@"%@\n%@", splitString[0], splitString[1]];
}

#pragma mark - NSTimer handling

- (void)tipsStringChange {
    [self.tipsLbl setText:[self randomTips]];
}

#pragma mark - Connect API

- (void)downloadDataFromServer {
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    [[JDDataManager sharedInstance] getJSONWithAPI:@"knowledge_list" parameter:nil result:^(NSDictionary *result) {
        if (result != nil) {
            if ([[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"DELETE FROM `knowledge`"]]) {
            
                for (NSDictionary *tempDict in result[@"knowledge"]) {
                    [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:[self createInsertStatementWithTableName:@"knowledge" dictionary:tempDict]]];
                }
            }
        }
    } errorMsg:^(NSString *errorMsg) {
        
    }];
    
    [[JDDataManager sharedInstance] getJSONWithAPI:@"document_list" parameter:nil result:^(NSDictionary *result) {
        if (result != nil) {
            if ([[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"DELETE FROM `document`"]]) {
                
                for (NSDictionary *tempDict in result[@"document"]) {
                    [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:[self createInsertStatementWithTableName:@"document" dictionary:tempDict]]];
                }
            }
        }
    } errorMsg:^(NSString *errorMsg) {
        
    }];
    
    [[JDDataManager sharedInstance] getJSONWithAPI:@"news_list" parameter:nil result:^(NSDictionary *result) {
        if (result != nil) {
            if ([[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"DELETE FROM `news`"]]) {
                
                for (NSDictionary *tempDict in result[@"news"]) {
                    [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:[self createInsertStatementWithTableName:@"news" dictionary:tempDict]]];
                }
            }
        }
    } errorMsg:^(NSString *errorMsg) {
        
    }];
}

#pragma mark - Create insert query

- (NSString *)createInsertStatementWithTableName:(NSString *)tableName dictionary:(NSDictionary *)dict {
    NSString *query1 = [NSString stringWithFormat:@"INSERT INTO `%@` (", tableName];
    NSString *query2 = @") VALUES (";
    
    NSUInteger index = 0;
    for (NSString *attribute in dict) {
        if (index == 0) {
            query1 = [query1 stringByAppendingFormat:@"`%@`", attribute];
            query2 = [query2 stringByAppendingFormat:@"'%@'", dict[attribute]];
        }
        else {
            query1 = [query1 stringByAppendingFormat:@", `%@`", attribute];
            query2 = [query2 stringByAppendingFormat:@", '%@'", dict[attribute]];
        }
        
        index++;
    }
    
    return [NSString stringWithFormat:@"%@%@)", query1, query2];
}

@end
