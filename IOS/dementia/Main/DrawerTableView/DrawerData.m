//
//  DrawerData.m
//  dementia
//
//

#import "DrawerData.h"

@implementation DrawerData

- (id)initWithData:(NSDictionary *)dict {
    self = [super init];
    if (self) {
        self.backgroundImage = [UIImage imageNamed:dict[@"backgroundImageName"]];
        self.itemName = LocalizedString(dict[@"itemName"]);
    }
    return self;
}

@end
