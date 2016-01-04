//
//  ContactCell.m
//  dementia
//
//

#import "ContactCell.h"

@implementation ContactCell

- (void)setUpViewWithData:(ContactData *)data {
    [self setUpTextFontSize];
    
    [self.photoImgView setImage:data.memberPhoto];
    [self.memberNameLbl setText:data.memberName];
    [self.callMemberBtn setTitle:data.memberPhone forState:UIControlStateNormal];

    if ([[[JDDataManager sharedInstance] returnGameMode] isEqualToString:@"Full"]) {
        self.editMemberBtn.hidden = false;
    } else {
        self.editMemberBtn.hidden = true;
    }
}

- (void)setUpTextFontSize {
    [self.memberNameLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.callMemberBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(18.0f)]];
}

@end
