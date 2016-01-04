//
//  UIView+UIComponent.m
//  
//
//

#import "UIView+UIComponent.h"

@implementation UIView (UIComponent)

- (void)setTextFieldEdge {
    if ([self isKindOfClass:[UITextField class]]) {
        UIView *leftView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, self.frame.size.height)];
        leftView.backgroundColor = ((UITextField *)self).backgroundColor;
        ((UITextField *)self).leftView = leftView;
        ((UITextField *)self).leftViewMode = UITextFieldViewModeAlways;
    }
    else if ([self isKindOfClass:[UIView class]]) {
        for (UIView *view in self.subviews) {
            if ([view isKindOfClass:[UITextField class]]) {
                UIView *leftView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, view.frame.size.height)];
                leftView.backgroundColor = ((UITextField *)view).backgroundColor;
                ((UITextField *)view).leftView = leftView;
                ((UITextField *)view).leftViewMode = UITextFieldViewModeAlways;
            }
        }
    }
}

- (void)addUnderlineToText {
    if ([self isKindOfClass:[UIButton class]]) {
        NSMutableAttributedString *mat = [((UIButton *)self).titleLabel.attributedText mutableCopy];
        [mat addAttributes:@{NSUnderlineStyleAttributeName: @(NSUnderlineStyleSingle)} range:NSMakeRange(0, mat.length)];
        [((UIButton *)self).titleLabel setAttributedText:mat];
    }
    else if ([self isKindOfClass:[UILabel class]]) {
        NSMutableAttributedString *mat = [((UILabel *)self).attributedText mutableCopy];
        [mat addAttributes:@{NSUnderlineStyleAttributeName: @(NSUnderlineStyleSingle)} range:NSMakeRange(0, mat.length)];
        [((UILabel *)self) setAttributedText:mat];
    }
    else if ([self isKindOfClass:[UITextField class]]) {
        NSMutableAttributedString *mat = [((UITextField *)self).attributedText mutableCopy];
        [mat addAttributes:@{NSUnderlineStyleAttributeName: @(NSUnderlineStyleSingle)} range:NSMakeRange(0, mat.length)];
        [((UITextField *)self) setAttributedText:mat];
    }
}

@end
