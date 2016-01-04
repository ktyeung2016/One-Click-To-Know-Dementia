//
//  ResourceDetailViewController.m
//  dementia
//
//

#import "ResourceDetailViewController.h"

@interface ResourceDetailViewController ()

@end

@implementation ResourceDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    
    [self setUpViewWithData];
}

- (void)viewDidLayoutSubviews {
    [super viewDidLayoutSubviews];
    
    [self.scrollBackground setContentSize:CGSizeMake(self.scrollBackground.frame.size.width,
                                                     self.contentView.frame.origin.y + self.contentView.frame.size.height)];
}

- (void)setUpLocalizationView {
    [self.districtMark setText:LocalizedString(@"RESOURCE_DETAIL_DISTRICT")];
    [self.targetMark setText:LocalizedString(@"RESOURCE_DETAIL_TARGET")];
    [self.phoneMark setText:LocalizedString(@"RESOURCE_DETAIL_PHONE")];
    [self.websiteMark setText:LocalizedString(@"RESOURCE_DETAIL_WEBSITE")];
    [self.remarkMark setText:LocalizedString(@"RESOURCE_DETAIL_REMARK")];
}

- (void)setUpTextFontSize {
    [self.organizationLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(25.0f)]];
    [self.serviceLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(23.0f)]];
    
    for (UILabel *lbl in self.contentView.subviews) {
        if ([lbl isKindOfClass:[UILabel class]]) {
            [lbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        }
    }
    
    [self. websiteBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
}

- (void)setUpViewWithData {
    NSString *serviceTitle = [NSString stringWithFormat:@"%@", self.resourceDetailDict[[NSString stringWithFormat:@"service_name1_%@", LanguageForDataAttribute]]];
    
    NSString *serviceName2 = self.resourceDetailDict[[NSString stringWithFormat:@"service_name2_%@", LanguageForDataAttribute]];
    if (serviceName2 != nil && ![serviceName2 isEqual:@""]) {
        serviceTitle = [serviceTitle stringByAppendingFormat:@"\n-%@", serviceName2];
    }
    
    [self.organizationLbl setText:self.resourceDetailDict[[NSString stringWithFormat:@"organization_%@", LanguageForDataAttribute]]];
    [self.serviceLbl setText:serviceTitle];
    
    [self.districtLbl setText:self.resourceDetailDict[[NSString stringWithFormat:@"district_%@", LanguageForDataAttribute]]];
    [self.phoneLbl setText:self.resourceDetailDict[@"phone"]];
    [self.remarkLbl setText:self.resourceDetailDict[[NSString stringWithFormat:@"remark_%@", LanguageForDataAttribute]]];
    if ([[self.remarkLbl text] isEqualToString:@""]) {
        [self.remarkMark setIsAccessibilityElement:TRUE];
        [self.remarkMark setAccessibilityLabel:[[self.remarkMark text] stringByAppendingString:@"無備註"]];
    }
    
    NSArray *targetAttributeList = @[@"first", @"second", @"third", @"four", @"family", @"helper"];
    NSArray *imageNameList = @[@"Level_One", @"Level_Two", @"Level_Three", @"Level_Four", @"Level_Family", @"Level_Helper"];
    
    NSMutableArray *existTargetList = [NSMutableArray array];
    NSString *targetMarkHint = @"";
    
    for (int i=0; i<[targetAttributeList count]; i++) {
        if (![self.resourceDetailDict[targetAttributeList[i]] isEqual:@""]) {
            [existTargetList addObject:@(i)];
        }
    }
    
    for (int i=0; i<[existTargetList count]; i++) {
        if ([self.targetView.subviews count] == 0) {
            UIImage *image = [UIImage imageNamed:imageNameList[[existTargetList[i] integerValue]]];
            UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, image.size.width, image.size.height)];
            [imageView setImage:image];
            
            [self.targetView addSubview:imageView];
        }
        else {
            UIImageView *imageView = self.targetView.subviews.lastObject;
            UIImage *image = [UIImage imageNamed:imageNameList[[existTargetList[i] integerValue]]];
            CGRect newImageViewRect;
            
            switch ([existTargetList[i] integerValue]) {
                case 1: {
                    newImageViewRect = CGRectMake((imageView.frame.origin.y == 0) ? imageView.frame.origin.x + imageView.frame.size.width + 8 : 0,
                                                  0,
                                                  image.size.width,
                                                  image.size.height);
                    break;
                }
                case 2: {
                    newImageViewRect = CGRectMake((imageView.frame.origin.y == 0) ? imageView.frame.origin.x + imageView.frame.size.width + 8 : 0,
                                                  0,
                                                  image.size.width,
                                                  image.size.height);
                    break;
                }
                case 3: {
                    newImageViewRect = CGRectMake((imageView.frame.origin.y == 0) ? imageView.frame.origin.x + imageView.frame.size.width + 8 : 0,
                                                  0,
                                                  image.size.width,
                                                  image.size.height);
                    break;
                }
                case 4: {
                    newImageViewRect = CGRectMake(0,
                                                  ([existTargetList containsObject:@0] ||
                                                   [existTargetList containsObject:@1] ||
                                                   [existTargetList containsObject:@2] ||
                                                   [existTargetList containsObject:@3]) ? 37 : 0,
                                                  image.size.width,
                                                  image.size.height);
                    break;
                }
                case 5: {
                    newImageViewRect = CGRectMake(([existTargetList containsObject:@4]) ? 65 : 0,
                                                  ([existTargetList containsObject:@0] ||
                                                   [existTargetList containsObject:@1] ||
                                                   [existTargetList containsObject:@2] ||
                                                   [existTargetList containsObject:@3]) ? 37 : 0,
                                                  image.size.width,
                                                  image.size.height);
                    break;
                }
            }
            
            UIImageView *newImageView = [[UIImageView alloc] initWithFrame:newImageViewRect];
            [newImageView setImage:image];
            
            [self.targetView addSubview:newImageView];
        }
        switch ([existTargetList[i] integerValue]) {
            case 0: {
                targetMarkHint = [targetMarkHint stringByAppendingString:@"輕"];
                break;
            }
            case 1: {
                targetMarkHint = [targetMarkHint stringByAppendingString:@"初"];
                break;
            }
            case 2: {
                targetMarkHint = [targetMarkHint stringByAppendingString:@"中"];
                break;
            }
            case 3: {
                targetMarkHint = [targetMarkHint stringByAppendingString:@"晚"];
                break;
            }
            case 4: {
                targetMarkHint = [targetMarkHint stringByAppendingString:@"家屬"];
                break;
            }
            case 5: {
                targetMarkHint = [targetMarkHint stringByAppendingString:@"外傭"];
                break;
            }
        }
    }
    [self.targetMark setIsAccessibilityElement:TRUE];
    [self.targetMark setAccessibilityLabel:[LocalizedString(@"RESOURCE_DETAIL_TARGET") stringByAppendingString:targetMarkHint]];
    
    /*
     Tune elements' height
     */
    CGSize organizationLblConstraintSize = {[[UIScreen mainScreen] bounds].size.width - 20, 20000};
    CGSize organizationLblNeededSize = [self.resourceDetailDict[[NSString stringWithFormat:@"organization_%@", LanguageForDataAttribute]]
                                        sizeWithFont:[UIFont systemFontOfSize:FontSizeLarge(25.0f)]
                                        constrainedToSize:organizationLblConstraintSize
                                        lineBreakMode:NSLineBreakByCharWrapping];
    
    if (organizationLblNeededSize.height >= 30.0f) {
        [self.organizationLblHeightConstraint setConstant:organizationLblNeededSize.height + 1];
        
        [self.view layoutIfNeeded];
    }
    
    CGSize serviceLblConstraintSize = {[[UIScreen mainScreen] bounds].size.width - 20, 20000};
    CGSize serviceLblNeededSize = [serviceTitle
                                   sizeWithFont:[UIFont systemFontOfSize:FontSizeLarge(23.0f)]
                                   constrainedToSize:serviceLblConstraintSize
                                   lineBreakMode:NSLineBreakByCharWrapping];
    
    if (serviceLblNeededSize.height >= 28.0f) {
        [self.serviceLblHeightConstraint setConstant:serviceLblNeededSize.height + 1];
        
        [self.view layoutIfNeeded];
    }
    
    CGSize districtLblConstraintSize = {[[UIScreen mainScreen] bounds].size.width - 101, 20000};
    CGSize districtLblNeededSize = [self.resourceDetailDict[[NSString stringWithFormat:@"district_%@", LanguageForDataAttribute]]
                                   sizeWithFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]
                                   constrainedToSize:districtLblConstraintSize
                                   lineBreakMode:NSLineBreakByCharWrapping];
    
    if (districtLblNeededSize.height >= 23.0f) {
        self.contentViewHeightConstraint.constant += districtLblNeededSize.height + 1 - self.districtLblHeightConstraint.constant;
        [self.districtLblHeightConstraint setConstant:districtLblNeededSize.height + 1];
        
        [self.view layoutIfNeeded];
    }
    
    CGSize remarkLblConstraintSize = {[[UIScreen mainScreen] bounds].size.width - 101, 20000};
    CGSize remarkLblNeededSize = [self.resourceDetailDict[[NSString stringWithFormat:@"remark_%@", LanguageForDataAttribute]]
                                    sizeWithFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]
                                    constrainedToSize:remarkLblConstraintSize
                                    lineBreakMode:NSLineBreakByCharWrapping];
    
    if (remarkLblNeededSize.height >= 23.0f) {
        self.contentViewHeightConstraint.constant += remarkLblNeededSize.height + 1 - self.remarkLblHeightConstraint.constant;
        [self.remarkLblHeightConstraint setConstant:remarkLblNeededSize.height + 1];
        
        [self.view layoutIfNeeded];
    }
    
    if (([existTargetList containsObject:@0] || [existTargetList containsObject:@1] || [existTargetList containsObject:@2] || [existTargetList containsObject:@3]) &&
        ([existTargetList containsObject:@4] || [existTargetList containsObject:@5])) {
        self.contentViewHeightConstraint.constant += 37;
        self.targetViewHeightConstraint.constant += 37;
        
        [self.view layoutIfNeeded];
    }
}

#pragma mark - IBAction

- (IBAction)phoneBtnClick:(id)sender {
    if ([[UIDevice currentDevice].model isEqualToString:@"iPhone"] &&
        [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", self.phoneLbl]]]) {
        
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", self.phoneLbl]]];
    }
    else {
        ShowAlertViewNormal(@"", LocalizedString(@"ALERT_NO_PHONE"), nil, LocalizedString(@"BUTTON_CONFIRM"), nil, 0);
    }
}

- (IBAction)websiteBtnClick:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:self.resourceDetailDict[@"website"]]];
}

@end
