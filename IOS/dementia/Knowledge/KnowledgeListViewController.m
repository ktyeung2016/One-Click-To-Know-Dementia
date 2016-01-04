//
//  KnowledgeListViewController.m
//  dementia
//
//

#import "KnowledgeListViewController.h"

#import "KnowledgeCell.h"
#import "KnowledgeData.h"

#import "KnowledgeDetailViewController.h"

@interface KnowledgeListViewController ()

@property (strong, nonatomic) NSMutableArray *itemList;
@property (strong, nonatomic) NSMutableArray *itemDictList;

@end

@implementation KnowledgeListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpData];
}

- (void)setUpData {
    self.itemList = [NSMutableArray array];
    self.itemDictList = [NSMutableArray array];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `knowledge`"]
                                    keys:KnowledgeTableAttributes]) {
        
        KnowledgeData *data = [[KnowledgeData alloc] initWithData:tempDict[[NSString stringWithFormat:@"title_%@", LanguageForDataAttribute]]];
        [self.itemList addObject:data];
        
        [self.itemDictList addObject:tempDict];
    }
    
    [db close];
    
    if ([self.itemList count] == 0) {
        ShowAlertViewNormal(LocalizedString(@"ALERT_NO_DATA"), LocalizedString(@"ALERT_PLEASE_CONNECT_TO_NETWORK"), self, LocalizedString(@"BUTTON_CONFIRM"), nil, 1);
    }
}

#pragma mark - UITableView datasource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.itemList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    KnowledgeCell *cell = [tableView dequeueReusableCellWithIdentifier:@"KnowledgeCell" forIndexPath:indexPath];
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
    
    [cell setUpViewWithData:self.itemList[indexPath.row]];
    
    return cell;
}

#pragma mark - UITableView delegate

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    KnowledgeData *data = self.itemList[indexPath.row];
    
    CGSize constraintSize = {[[UIScreen mainScreen] bounds].size.width - 72, 20000};
    CGSize neededSize = [data.knowledgeTitle
                         sizeWithFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]
                         constrainedToSize:constraintSize
                         lineBreakMode:NSLineBreakByCharWrapping];
    
    if (neededSize.height <= 23.0f) {
        return 50.0f;
    }
    else {
        return neededSize.height - 22.0f + 50.0f;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    KnowledgeDetailViewController *knowledgeDetailVC = [self.storyboard instantiateViewControllerWithIdentifier:@"KnowledgeDetailViewController"];
    [knowledgeDetailVC setDetailDict:self.itemDictList[indexPath.row]];
    
    [self.navigationController pushViewController:knowledgeDetailVC animated:YES];
}

#pragma mark - UIAlertView delegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    [self.navigationController popViewControllerAnimated:YES];
}

@end
