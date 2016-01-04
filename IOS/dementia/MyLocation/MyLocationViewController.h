//
//  MyLocationViewController.h
//  dementia
//
//

#import <GoogleMaps/GoogleMaps.h>

#import "BaseViewController.h"

@interface MyLocationViewController : BaseViewController <CLLocationManagerDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UIButton *reloadBtn;
@property (weak, nonatomic) IBOutlet UIButton *contactBtn;

@property (weak, nonatomic) IBOutlet UILabel *useMethodMark;
@property (weak, nonatomic) IBOutlet UILabel *demoLbl;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;

@property (weak, nonatomic) IBOutlet UIView *demoTopView;
@property (weak, nonatomic) IBOutlet UIView *demoLayerView;
@property (weak, nonatomic) IBOutlet UIView *demoLayerMask;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *demoTopViewHeightConstraint;

@property (assign, nonatomic) BOOL isModeDemo;

- (IBAction)nextBtnClick:(id)sender;

- (IBAction)reloadBtnClick:(id)sender;
- (IBAction)contactBtnClick:(id)sender;

@end
