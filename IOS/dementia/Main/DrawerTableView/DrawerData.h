//
//  DrawerData.h
//  dementia
//
//

#import <Foundation/Foundation.h>

@interface DrawerData : NSObject

@property (strong, nonatomic) UIImage *backgroundImage;
@property (strong, nonatomic) NSString *itemName;

- (id)initWithData:(NSDictionary *)dict;

@end
