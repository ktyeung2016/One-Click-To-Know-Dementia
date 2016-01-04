//
//  DrawerViewController.m
//  dementia
//
//

#import "DrawerViewController.h"

#import "DrawerHeaderCell.h"
#import "DrawerButtonCell.h"
#import "DrawerData.h"

#import "HomeViewController.h"

#import "ECSlidingViewController.h"

@interface DrawerViewController ()

@property (assign, nonatomic) CGFloat peekLeftAmount;

@property (strong, nonatomic) NSDictionary *drawerDict;

@property (strong, nonatomic) UINavigationController *baseNavigationController;

@end

@implementation DrawerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(updateDrawer)
                                                 name:@"Notification_UpdateMode"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(updateDrawer)
                                                 name:@"Notification_UpdateLocalizationView"
                                               object:nil];
    
    self.peekLeftAmount = 40.0f;
    [self.slidingViewController setAnchorLeftPeekAmount:self.peekLeftAmount];
    self.slidingViewController.underRightWidthLayout = ECVariableRevealWidth;
    
    self.baseNavigationController = (UINavigationController *)self.slidingViewController.topViewController;
    
    [self setUpData];
}

- (void)setUpData {
    self.drawerDict = @{@"Full": @{@"backgroundImageName": @[@"Drawer_Home",
                                                             @"Drawer_Knowledge",
                                                             @"Drawer_Resource",
                                                             @"Drawer_SelfAssignment",
                                                             @"Drawer_Game",
                                                             @"Drawer_MyLocation",
                                                             @"Drawer_Setting"],
                                   
                                   @"itemName": @[@"MAIN_HOMEPAGE",
                                                  @"MAIN_CATEGORY_KNOWLEDGE",
                                                  @"MAIN_CATEGORY_RESOURCE",
                                                  @"MAIN_CATEGORY_SELF_ASSIGNMENT",
                                                  @"MAIN_CATEGORY_GAME",
                                                  @"MAIN_CATEGORY_MY_LOCATION",
                                                  @"MAIN_CATEGORY_SETTING"],
                                   
                                   @"navigation": @[@"HomeViewController",
                                                    @"KnowledgeListViewController",
                                                    @"ResourceListViewController",
                                                    @"SelfAssignmentViewController",
                                                    @"GameListViewController",
                                                    @"MyLocationViewController",
                                                    @"SettingViewController"],
                                   
                                   @"storyboard": @[@"Main",
                                                    @"Knowledge",
                                                    @"Resource",
                                                    @"SelfAssignment",
                                                    @"Game",
                                                    @"MyLocation",
                                                    @"Setting"]},
                        
                        @"Game": @{@"backgroundImageName": @[@"Drawer_Home",
                                                             @"Drawer_Game",
                                                             @"Drawer_MyLocation",
                                                             @"Drawer_Call",
                                                             @"Drawer_Setting"],
                                   
                                   @"itemName": @[@"MAIN_HOMEPAGE",
                                                  @"MAIN_CATEGORY_GAME",
                                                  @"MAIN_CATEGORY_MY_LOCATION",
                                                  @"MAIN_CATEGORY_CALL",
                                                  @"MAIN_CATEGORY_SETTING"],
                                   
                                   @"navigation": @[@"HomeViewController",
                                                    @"GameListViewController",
                                                    @"MyLocationViewController",
                                                    @"ContactFamilyViewController",
                                                    @"SettingViewController"],
                                   
                                   @"storyboard": @[@"Main",
                                                    @"Game",
                                                    @"MyLocation",
                                                    @"MyLocation",
                                                    @"Setting"]}};
}

- (void)updateDrawer {
    [self.buttonTableView reloadData];
}

#pragma mark - UITableView datasource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 0) {
        return 1;
    }
    else {
        return [self.drawerDict[[[JDDataManager sharedInstance] returnGameMode]][@"itemName"] count];
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        DrawerHeaderCell *cell = [tableView dequeueReusableCellWithIdentifier:@"DrawerHeaderCell"];
        [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
        
        return cell;
    }
    else {
        DrawerButtonCell *cell = [tableView dequeueReusableCellWithIdentifier:@"DrawerButtonCell" forIndexPath:indexPath];
        [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
        
        DrawerData *data = [[DrawerData alloc]
                            initWithData:@{@"backgroundImageName": self.drawerDict[[[JDDataManager sharedInstance] returnGameMode]][@"backgroundImageName"][indexPath.row],
                                           @"itemName": self.drawerDict[[[JDDataManager sharedInstance] returnGameMode]][@"itemName"][indexPath.row]}];
        
        [cell setUpViewWithData:data];
        
        return cell;
    }
}

#pragma mark - UITableView delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    HomeViewController *homeVC;
    
    for (UIViewController *vc in self.slidingViewController.topViewController.childViewControllers) {
        if ([vc isKindOfClass:[HomeViewController class]]) {
            homeVC = (HomeViewController *)vc;
            
            [self.baseNavigationController popToViewController:vc animated:NO];
            
            break;
        }
    }
    
    if (indexPath.section == 0) {
        [homeVC pushControllerWithStoryboardName:@"MyLocation" identifier:@"MyLocationViewController" animated:NO];
    }
    else {
        if (indexPath.row != 0) {
            [homeVC
             pushControllerWithStoryboardName:self.drawerDict[[[JDDataManager sharedInstance] returnGameMode]][@"storyboard"][indexPath.row]
             identifier:self.drawerDict[[[JDDataManager sharedInstance] returnGameMode]][@"navigation"][indexPath.row]
             animated:NO];
        }
    }
    
    [self.slidingViewController resetTopView];
}

@end
