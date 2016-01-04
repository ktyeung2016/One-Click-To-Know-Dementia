//
//  ResourceListViewController.m
//  dementia
//
//

#import "ResourceListViewController.h"

#import "ResourceListCell.h"
#import "ResourceListData.h"
#import "ResourceRemarkCell.h"

#import "ResourceDetailViewController.h"

@interface ResourceListViewController ()

@property (assign, nonatomic) NSUInteger lastestSectionNumber;
@property (assign, nonatomic) NSUInteger selectedDistrict;
@property (assign, nonatomic) NSUInteger selectedType;

@property (strong, nonatomic) NSDictionary *districtDetailDict;

@property (strong, nonatomic) NSArray *districtLblArray;
@property (strong, nonatomic) NSArray *typeLblArray;

@property (strong, nonatomic) NSMutableArray *documentTitleList;
@property (strong, nonatomic) NSMutableArray *headerList;
@property (strong, nonatomic) NSMutableArray *itemList;
@property (strong, nonatomic) NSMutableArray *itemDataList;

@end

@implementation ResourceListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.lastestSectionNumber = 1;
    self.selectedDistrict = 0;
    self.selectedType = 0;
    
    self.districtLblArray = @[LocalizedString(@"ALL"),
                              LocalizedString(@"RESOURCE_DISTRICT_HI"),
                              LocalizedString(@"RESOURCE_DISTRICT_KL"),
                              LocalizedString(@"RESOURCE_DISTRICT_NT"),
                              LocalizedString(@"RESOURCE_DISTRICT_IL"),
                              LocalizedString(@"BUTTON_CANCEL")];
    
    self.typeLblArray = @[LocalizedString(@"ALL"),
                          LocalizedString(@"RESOURCE_TYPE_LIGHT"),
                          LocalizedString(@"RESOURCE_TYPE_EARLY"),
                          LocalizedString(@"RESOURCE_TYPE_MIDDLE"),
                          LocalizedString(@"RESOURCE_TYPE_LATER"),
                          LocalizedString(@"RESOURCE_TYPE_CAREGIVERS"),
                          LocalizedString(@"BUTTON_CANCEL")];
    
    [self setUpLocalizationView];
    [self setUpData];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"MAIN_CATEGORY_RESOURCE")];
    
    [self.districtMark setText:LocalizedString(@"RESOURCE_DISTRICT")];
    [self.typeMark setText:LocalizedString(@"RESOURCE_TYPE")];
    
    [self.districtLbl setText:LocalizedString(@"ALL")];
    [self.btnDistrict setAccessibilityLabel:[[self.districtLbl text] stringByAppendingString:@"地區"]];;
    [self.typeLbl setText:LocalizedString(@"ALL")];
    [self.btnType setAccessibilityLabel:[[self.typeLbl text] stringByAppendingString:@"類別"]];
}

- (void)setUpData {
    self.documentTitleList = [NSMutableArray array];
    self.headerList = [NSMutableArray array];
    self.itemList = [NSMutableArray array];
    self.itemDataList = [NSMutableArray array];
    
    FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
    [db open];
    
    NSString *query = [NSString stringWithFormat:@"SELECT DISTINCT `title_%@` FROM `document`", LanguageForDataAttribute];
    
    for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                    getDataFromTableWithQuery:[db executeQuery:query]
                                    keys:@[[NSString stringWithFormat:@"title_%@", LanguageForDataAttribute]]]) {
        
        UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, [[UIScreen mainScreen] bounds].size.width, 40)];
        [headerView setBackgroundColor:[UIColor whiteColor]];
        
        UIImage *markerImg = [UIImage imageNamed:@"Marker_Yellow"];
        UIImageView *markerImgView = [[UIImageView alloc] initWithFrame:CGRectMake(10, 0, markerImg.size.width, markerImg.size.height)];
        [markerImgView setImage:markerImg];
        [markerImgView setCenter:CGPointMake(markerImgView.center.x, headerView.center.y)];
        [headerView addSubview:markerImgView];
        
        UILabel *titleLbl = [[UILabel alloc] initWithFrame:CGRectMake(markerImgView.frame.origin.x + markerImgView.frame.size.width + 10, 0, 0, 0)];
        [titleLbl setText:tempDict[[NSString stringWithFormat:@"title_%@", LanguageForDataAttribute]]];
        [titleLbl sizeToFit];
        [titleLbl setCenter:CGPointMake(titleLbl.center.x, headerView.center.y)];
        [headerView addSubview:titleLbl];
        
        UIView *separator = [[UIView alloc] initWithFrame:CGRectMake(0, headerView.frame.size.height - 1, headerView.frame.size.width, 1)];
        [separator setBackgroundColor:[UIColor colorWithRed:0.667 green:0.667 blue:0.667 alpha:0.32]];
        [headerView addSubview:separator];
        
        [self.headerList addObject:headerView];
        
        [self.documentTitleList addObject:tempDict[[NSString stringWithFormat:@"title_%@", LanguageForDataAttribute]]];
    }
    
    for (NSString *title in self.documentTitleList) {
        NSMutableArray *dataList = [NSMutableArray array];
        NSMutableArray *dictList = [NSMutableArray array];
        
        NSString *query = [NSString stringWithFormat:@"SELECT * FROM `document` WHERE `title_%@` = '%@'", LanguageForDataAttribute, title];
        
        switch (self.selectedDistrict) {
            case 1:
                query = [query stringByAppendingString:@" AND (`district_zh` LIKE '%港島%' OR `district_zh` LIKE '%中西區%' OR `district_zh` LIKE '%灣仔%' OR `district_zh` LIKE '%東區%' OR `district_zh` LIKE '%南區%')"];
                break;
                
            case 2:
                query = [query stringByAppendingString:@" AND (`district_zh` LIKE '%九龍%' OR `district_zh` LIKE '%九龍城%' OR `district_zh` LIKE '%黃大仙%' OR `district_zh` LIKE '%觀塘%' OR `district_zh` LIKE '%油尖旺%' OR `district_zh` LIKE '%深水埗%')"];
                break;
                
            case 3:
                query = [query stringByAppendingString:@" AND (`district_zh` LIKE '%新界%' OR `district_zh` LIKE '%荃灣%'OR `district_zh` LIKE '%葵青%' OR `district_zh` LIKE '%西貢%' OR `district_zh` LIKE '%沙田%' OR `district_zh` LIKE '%大埔%' OR `district_zh` LIKE '%北區%' OR `district_zh` LIKE '%屯門%' OR `district_zh` LIKE '%元朗%' OR `district_zh` LIKE '%將軍澳%')"];
                break;
                
            case 4:
                query = [query stringByAppendingString:@" AND `district_zh` LIKE '%離島%'"];
                break;
        }
        
        switch (self.selectedType) {
            case 1:
                query = [query stringByAppendingString:@" AND `first` = '1'"];
                break;
                
            case 2:
                query = [query stringByAppendingString:@" AND `second` = '1'"];
                break;
                
            case 3:
                query = [query stringByAppendingString:@" AND `third` = '1'"];
                break;
                
            case 4:
                query = [query stringByAppendingString:@" AND `four` = '1'"];
                break;
                
            case 5:
                query = [query stringByAppendingString:@" AND (`family` = '1' OR `helper` = '1')"];
                break;
        }
        
        for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                        getDataFromTableWithQuery:[db executeQuery:query]
                                        keys:DocumentTableAttributes]) {
            
            NSString *resourceTitle = [NSString stringWithFormat:@"%@", tempDict[[NSString stringWithFormat:@"service_name1_%@", LanguageForDataAttribute]]];
            
            NSString *resourceServiceName2 = tempDict[[NSString stringWithFormat:@"service_name2_%@", LanguageForDataAttribute]];
            if (resourceServiceName2 != nil && ![resourceServiceName2 isEqual:@""]) {
                resourceTitle = [resourceTitle stringByAppendingFormat:@"\n-%@", resourceServiceName2];
            }
            
            ResourceListData *data = [[ResourceListData alloc] initWithData:resourceTitle];
            [dataList addObject:data];
            
            [dictList addObject:tempDict];
        }
        
        [self.itemList addObject:dataList];
        [self.itemDataList addObject:dictList];
    }
    
    [db close];
    
    self.lastestSectionNumber = [self.itemList count];
    
    [self.resourcesTableView reloadData];
    
    for (int i=0; i<self.lastestSectionNumber; i++) {
        if ([self.resourcesTableView isOpenSection:i]) {
        [self.resourcesTableView closeSection:i animated:YES];
        }
    }
    
    [self.resourcesTableView openSection:self.lastestSectionNumber animated:NO];
    [self.resourcesTableView setExclusiveSections:NO];
    
    if ([self.headerList count] == 0) {
        ShowAlertViewNormal(LocalizedString(@"ALERT_NO_DATA"), LocalizedString(@"ALERT_PLEASE_CONNECT_TO_NETWORK"), self, LocalizedString(@"BUTTON_CONFIRM"), nil, 3);
    }
}

#pragma mark - IBAction

- (IBAction)districtBtnClick:(id)sender {
    ShowAlertView(LocalizedString(@"ALERT_CHOOSE"), nil, self, self.districtLblArray, 1)
}

- (IBAction)typeBtnClick:(id)sender {
    ShowAlertView(LocalizedString(@"ALERT_CHOOSE"), nil, self, self.typeLblArray, 2)
}

#pragma mark - UIAlertView delegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if ([alertView tag] == 1) {
        if (buttonIndex < [self.districtLblArray count] - 1) {
            [self.districtLbl setText:self.districtLblArray[buttonIndex]];
            [self.btnDistrict setAccessibilityLabel:[[self.districtLbl text] stringByAppendingString:@"地區"]];
            self.selectedDistrict = buttonIndex;
            
            [self setUpData];
        }
    }
    else if ([alertView tag] == 2) {
        if (buttonIndex < [self.typeLblArray count] - 1) {
            [self.typeLbl setText:self.typeLblArray[buttonIndex]];
            [self.btnType setAccessibilityLabel:[[self.typeLbl text] stringByAppendingString:@"類別"]];
            self.selectedType = buttonIndex;
            
            [self setUpData];
        }
    }
    else if ([alertView tag] == 3) {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

#pragma mark - UITableView datasource

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == self.lastestSectionNumber) {
        ResourceRemarkCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ResourceRemarkCell"];
        [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
        
        return cell;
    }
    else {
        ResourceListCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ResourceListCell" forIndexPath:indexPath];
        [cell.contentView setBackgroundColor:[UIColor colorWithRed:0.96 green:0.87 blue:0.62 alpha:1]];
        
        [cell setUpViewWithData:self.itemList[indexPath.section][indexPath.row]];
        
        return cell;
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == self.lastestSectionNumber) {
        return 1;
    }
    else {
        return [self.itemList[section] count];
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.lastestSectionNumber + 1;
}

#pragma mark - UITableView delegate

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    if (section == self.lastestSectionNumber) {
        return nil;
    }
    else {
        return self.headerList[section];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    if (section == self.lastestSectionNumber) {
        return 0.0f;
    }
    else {
        return 40.0f;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == self.lastestSectionNumber) {
        return 310.0f;
    }
    else {
        ResourceListData *data = self.itemList[indexPath.section][indexPath.row];
        
        CGSize constraintSize = {[[UIScreen mainScreen] bounds].size.width - 100, 20000};
        CGSize neededSize = [data.resourceTitle
                             sizeWithFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]
                             constrainedToSize:constraintSize
                             lineBreakMode:NSLineBreakByCharWrapping];
        
        if (neededSize.height <= 23.0f) {
            return 50.0f;
        }
        else {
            return neededSize.height - 22.0f + 50.0f;
        }
        
        return 50.0f;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section != self.lastestSectionNumber) {
        ResourceDetailViewController *detailVC = [self.storyboard instantiateViewControllerWithIdentifier:@"ResourceDetailViewController"];
        [detailVC setResourceDetailDict:self.itemDataList[indexPath.section][indexPath.row]];
        
        [self.navigationController pushViewController:detailVC animated:YES];
    }
}

@end
