//
//  ContactData.h
//  dementia
//
//

#import <Foundation/Foundation.h>

@interface ContactData : NSObject

@property (strong, nonatomic) UIImage *memberPhoto;
@property (strong, nonatomic) NSString *memberName;
@property (strong, nonatomic) NSString *memberPhone;
@property (strong, nonatomic) NSString *memberEmail;

- (id)initWithData:(NSDictionary *)dict;

@end
