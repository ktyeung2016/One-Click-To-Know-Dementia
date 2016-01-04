//
//  ResourceRemarkCell.m
//  dementia
//
//

#import "ResourceRemarkCell.h"

@implementation ResourceRemarkCell

- (void)awakeFromNib {
    [self setUpLocalizationView];
}

- (void)setUpLocalizationView {
    [self.remarkLbl setText:LocalizedString(@"REMARK")];
    [self.targetImageIntroductionLbl setText:LocalizedString(@"RESOURCE_REMARK_INTRODUCTION")];
    [self.lightLbl setText:LocalizedString(@"RESOURCE_REMARK_LIGHT")];
    [self.earlyLbl setText:LocalizedString(@"RESOURCE_REMARK_EARLY")];
    [self.middleLbl setText:LocalizedString(@"RESOURCE_REMARK_MIDDLE")];
    [self.laterLbl setText:LocalizedString(@"RESOURCE_REMARK_LATER")];
    [self.familyLbl setText:LocalizedString(@"RESOURCE_REMARK_FAMILY")];
    [self.caregiversLbl setText:LocalizedString(@"RESOURCE_REMARK_CAREGIVERS")];
}

@end
