//
//  UpdateContactViewController.m
//  dementia
//
//

#import "UpdateContactViewController.h"

#import "ContactFamilyViewController.h"

#import "UIView+UIComponent.h"

@interface UpdateContactViewController ()

@property (assign, nonatomic) NSUInteger currentPageNumber;

@property (strong, nonatomic) NSArray *demoMsgList;

@property (strong, nonatomic) NSString *photoPath;
@property (assign, nonatomic) BOOL isPhotoTook;

@property (strong, nonatomic) UITextField *activeTextField;
@property (strong, nonatomic) UITextView *activeTextView;

@end

@implementation UpdateContactViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.photoPath = @"";
    
    self.currentPageNumber = 3;
    
    self.demoMsgList = @[@"bottom", @"bottom", @"bottom", @"bottom", @"bottom", @"bottom", @"middle"];
    
    [self setUpLocalizationView];
    [self setUpTextFontSize];
    [self.view setTextFieldEdge];
    
    [self setUpDemoView];
    [self setUpData];
    [self setUpDemoData];
    
    UITapGestureRecognizer *gestureRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(demoLayerViewTap)];
    [self.demoLayerView addGestureRecognizer:gestureRecognizer];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardDidShow:)
                                                 name:@"UIKeyboardDidShowNotification"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:@"UIKeyboardWillHideNotification"
                                               object:nil];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"UIKeyboardDidShowNotification" object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"UIKeyboardWillHideNotification" object:nil];
}

- (void)setUpLocalizationView {
    [self.navigationItem setTitle:LocalizedString(@"BUTTON_CONTACT_FAMILY")];
    
    [self.takePhotoBtn setTitle:LocalizedString(@"BUTTON_TAKE_PHOTO") forState:UIControlStateNormal];
    [self.choosePhotoBtn setTitle:LocalizedString(@"BUTTON_CHOOSE_PHOTO") forState:UIControlStateNormal];
    [self.nameMark setText:LocalizedString(@"MY_LOCATION_ADD_CONTACT_NAME")];
    [self.relationshipMark setText:LocalizedString(@"MY_LOCATION_ADD_CONTACT_RELATIONSHIP")];
    [self.phoneMark setText:LocalizedString(@"MY_LOCATION_ADD_CONTACT_PHONE")];
    [self.emailMark setText:LocalizedString(@"MY_LOCATION_ADD_CONTACT_EMAIL")];
    [self.remarkMark setText:LocalizedString(@"MY_LOCATION_ADD_CONTACT_REMARK")];
    //[self.cancelBtn setTitle:LocalizedString(@"BUTTON_CANCEL") forState:UIControlStateNormal];
    [self.finishBtn setTitle:LocalizedString(@"BUTTON_FINISH") forState:UIControlStateNormal];
    
    if (self.isModeDemo) {
        [self.useMethodMark setText:LocalizedString(@"LOCATION_DEMO_USE_METHOD")];
    }
}

- (void)setUpTextFontSize {
    for (UIView *view in self.view.subviews) {
        if ([view isKindOfClass:[UILabel class]]) {
            [((UILabel *)view) setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        }
        else if ([view isKindOfClass:[UIButton class]]) {
            [((UIButton *)view).titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        }
        else if ([view isKindOfClass:[UITextField class]]) {
            [((UITextField *)view) setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        }
        else if ([view isKindOfClass:[UITextView class]]) {
            [((UITextView *)view) setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
        }
    }
    
    [self.cancelBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.finishBtn.titleLabel setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    
    if (self.isModeDemo) {
        [self.useMethodMark setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        [self.pagesLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        [self.middleDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
        [self.bottomDemoLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    }
}

- (void)setUpDemoView {
    if (!self.isModeDemo) {
        [self.demoTopView setHidden:YES];
        [self.demoLayerView setHidden:YES];
        
        [self.demoTopViewHeightConstraint setConstant:0];
        
        [self.view layoutIfNeeded];
    }
}

- (void)setUpData {
    if (self.editContactDict != nil) {
        if (![self.editContactDict[@"file_media"] isEqual:@""]) {
            self.isPhotoTook = true;
            self.photoPath = self.editContactDict[@"file_media"];
            
            NSString *documentsPath = NSSearchPathForDirectoriesInDomains(NSDocumentationDirectory, NSUserDomainMask, YES)[0];
            
            NSString *folderPath = [documentsPath stringByAppendingPathComponent:@"Media/Contact"];
            
            NSString *filePath = [folderPath stringByAppendingPathComponent:self.editContactDict[@"file_media"]];
            
            [self.profileImgView setImage:[UIImage imageWithContentsOfFile:filePath]];
        }
        
        [self.nameTxtField setText:self.editContactDict[@"name"]];
        [self.relationshipTxtField setText:self.editContactDict[@"relationship"]];
        [self.phoneTxtField setText:self.editContactDict[@"phone"]];
        [self.emailTxtField setText:self.editContactDict[@"email"]];
        [self.remarkTxtView setText:self.editContactDict[@"remarks"]];
        
        self.cancelBtn.hidden = false;
    } else {
        self.cancelBtn.hidden = true;
    }
}

- (void)setUpDemoData {
    [self.nameTxtField setPlaceholder:(self.currentPageNumber >= 3) ? @"陳大文" : @""];
    [self.relationshipTxtField setPlaceholder:(self.currentPageNumber >= 4) ? @"兒子" : @""];
    [self.phoneTxtField setPlaceholder:(self.currentPageNumber >= 5) ? @"12345678" : @""];
    [self.emailTxtField setPlaceholder:(self.currentPageNumber >= 6) ? @"ChanTaiMan@email.com" : @""];
    
    if (self.currentPageNumber < 3) {
        [self.navigationController popViewControllerAnimated:NO];
    }
    else if (self.currentPageNumber == 10) {
        ContactFamilyViewController *contactFamilyVC = [self.storyboard instantiateViewControllerWithIdentifier:@"ContactFamilyViewController"];
        [contactFamilyVC setIsModeDemo:YES];
        [contactFamilyVC setCurrentPageNumber:10];
        
        [self.navigationController pushViewController:contactFamilyVC animated:NO];
    }
    else {
        [self.pagesLbl setText:[NSString stringWithFormat:@"%lu/10", (unsigned long)self.currentPageNumber]];
        
        NSString *localizationKey = [NSString stringWithFormat:@"LOCATION_DEMO_MSG_%lu", (unsigned long)self.currentPageNumber];
        
        if ([self.demoMsgList[self.currentPageNumber - 3] isEqualToString:@"middle"]) {
            [self.middleDemoView setHidden:NO];
            [self.bottomDemoView setHidden:YES];
            
            [self.middleDemoLbl setText:LocalizedString(localizationKey)];
        }
        else if ([self.demoMsgList[self.currentPageNumber - 3] isEqualToString:@"bottom"]) {
            [self.middleDemoView setHidden:YES];
            [self.bottomDemoView setHidden:NO];
            
            [self.bottomDemoLbl setText:LocalizedString(localizationKey)];
        }
        else {
            [self.middleDemoView setHidden:YES];
            [self.bottomDemoView setHidden:YES];
        }
    }
    
    UIBezierPath *overlayPath = [UIBezierPath bezierPathWithRect:self.view.bounds];
    UIBezierPath *transparentPath;
    CGRect screenRect = [UIScreen mainScreen].bounds;
    if (self.currentPageNumber==3) {
        CGRect nameTxtFieldRect = self.nameTxtField.bounds;
        nameTxtFieldRect.origin.x = screenRect.size.width-nameTxtFieldRect.size.width-15;
        nameTxtFieldRect.origin.y = 105;
        nameTxtFieldRect.size.width+=10;
        nameTxtFieldRect.size.height+=5;
        transparentPath = [UIBezierPath bezierPathWithRect:nameTxtFieldRect];
    } else if (self.currentPageNumber==4) {
        CGRect relationshipTxtFieldRect = self.relationshipTxtField.bounds;
        relationshipTxtFieldRect.origin.x = screenRect.size.width-relationshipTxtFieldRect.size.width-15;
        relationshipTxtFieldRect.origin.y = 145;
        relationshipTxtFieldRect.size.width+=10;
        relationshipTxtFieldRect.size.height+=5;
        transparentPath = [UIBezierPath bezierPathWithRect:relationshipTxtFieldRect];
    } else if (self.currentPageNumber==5) {
        CGRect phoneTxtFieldRect = self.phoneTxtField.bounds;
        phoneTxtFieldRect.origin.x = screenRect.size.width-phoneTxtFieldRect.size.width-15;
        phoneTxtFieldRect.origin.y = 184;
        phoneTxtFieldRect.size.width+=10;
        phoneTxtFieldRect.size.height+=5;
        transparentPath = [UIBezierPath bezierPathWithRect:phoneTxtFieldRect];
    } else if (self.currentPageNumber==6) {
        CGRect emailTxtFieldRect = self.emailTxtField.bounds;
        emailTxtFieldRect.origin.x = screenRect.size.width-emailTxtFieldRect.size.width-15;
        emailTxtFieldRect.origin.y = 223;
        emailTxtFieldRect.size.width+=10;
        emailTxtFieldRect.size.height+=5;
        transparentPath = [UIBezierPath bezierPathWithRect:emailTxtFieldRect];
    } else if (self.currentPageNumber==7) {
        CGRect remarkTxtViewRect = self.remarkTxtView.bounds;
        remarkTxtViewRect.origin.x = screenRect.size.width-remarkTxtViewRect.size.width-15;
        remarkTxtViewRect.origin.y = 264;
        remarkTxtViewRect.size.width+=10;
        remarkTxtViewRect.size.height+=5;
        transparentPath = [UIBezierPath bezierPathWithRect:remarkTxtViewRect];
    } else if (self.currentPageNumber==8) {
        CGRect photoRect = self.takePhotoBtn.bounds;
        photoRect.origin.x = screenRect.size.width-photoRect.size.width-15;
        photoRect.origin.y = 5;
        photoRect.size.width+=10;
        photoRect.size.height+=self.choosePhotoBtn.bounds.size.height+15;
        transparentPath = [UIBezierPath bezierPathWithRect:photoRect];
    } else if (self.currentPageNumber==9) {
        CGRect finishBtnRect = self.finishBtn.bounds;
        finishBtnRect.origin.x = screenRect.size.width/2+10;
        finishBtnRect.origin.y = screenRect.size.height-finishBtnRect.size.height-100;
        finishBtnRect.size.width+=10;
        finishBtnRect.size.height+=5;
        transparentPath = [UIBezierPath bezierPathWithRect:finishBtnRect];
    }

    [overlayPath appendPath:transparentPath];
    [overlayPath setUsesEvenOddFillRule:true];
    
    CAShapeLayer *dimLayer = [CAShapeLayer layer];
    dimLayer.path = overlayPath.CGPath;
    dimLayer.fillRule = kCAFillRuleEvenOdd;
    dimLayer.fillColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.7].CGColor;

    for (CALayer *layer in self.demoLayerMask.layer.sublayers) {
        [layer removeFromSuperlayer];
    }
    [self.demoLayerMask.layer addSublayer:dimLayer];
}

#pragma mark - IBAction

- (void)navigationBarBackBtnClick:(id)sender {
    if (self.isModeDemo) {
        [self.navigationController popToViewController:self.navigationController.viewControllers[2] animated:YES];
    }
    else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (IBAction)backBtnClick:(id)sender {
    if (self.currentPageNumber >= 3) {
        self.currentPageNumber--;
        
        [self setUpDemoData];
    }
}

- (IBAction)nextBtnClick:(id)sender {
    if (self.currentPageNumber < 10) {
        self.currentPageNumber++;
    }
    
    [self setUpDemoData];
}

- (IBAction)takePhotoBtnClick:(id)sender {
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    [picker setDelegate:self];
    [picker setAllowsEditing:YES];
    [picker setSourceType:UIImagePickerControllerSourceTypeCamera];
    
    [self presentViewController:picker animated:YES completion:nil];
}

- (IBAction)choosePhotoBtnClick:(id)sender {
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    [picker setDelegate:self];
    [picker setAllowsEditing:YES];
    [picker setSourceType:UIImagePickerControllerSourceTypePhotoLibrary];
    
    [self presentViewController:picker animated:YES completion:nil];
}

- (IBAction)cancelBtnClick:(id)sender {
    UIAlertView *deleteAlert = [[UIAlertView alloc] initWithTitle:@"" message:@"確定刪除家人？" delegate:self cancelButtonTitle:@"是" otherButtonTitles:@"否", nil];
    [deleteAlert show];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex==0) {
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        if (self.editContactDict != nil) {
            [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"DELETE FROM `contact` WHERE `id` = ?", self.editContactDict[@"id"]]];
        }
        
        [db close];
        
        if (self.contactUpdateBlock != nil) {
            self.contactUpdateBlock(true);
        }
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (IBAction)finishBtnClick:(id)sender {
    if ([self.nameTxtField.text isEqualToString:@""]) {
        [self.view makeToast:[NSString stringWithFormat:@"%@%@", LocalizedString(@"ALERT_PLEASE_ENTER"), LocalizedString(@"MY_LOCATION_ADD_CONTACT_NAME")]];
    }
    else if ([self.phoneTxtField.text isEqualToString:@""]) {
        [self.view makeToast:[NSString stringWithFormat:@"%@%@", LocalizedString(@"ALERT_PLEASE_ENTER"), LocalizedString(@"MY_LOCATION_ADD_CONTACT_PHONE")]];
    }
    else {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        BOOL query;
        
        if (self.editContactDict == nil) {
            query = [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"INSERT INTO `contact` (`name`, `relationship`, `phone`, `email`, `remarks`, `file_media`, `create_user`) VALUES (?, ?, ?, ?, ?, ?, ?)", self.nameTxtField.text, self.relationshipTxtField.text, self.phoneTxtField.text, self.emailTxtField.text, self.remarkTxtView.text, self.photoPath, [userDefaults objectForKey:@"userInfo"][@"id"]]];
        }
        else {
            query = [[[JDDataManager sharedInstance] dbHepler] updateTableDataWithQuery:[db executeUpdate:@"UPDATE `contact` SET `name` = ?, `relationship` = ?, `phone` = ?, `email` = ?, `remarks` = ?, `file_media` = ? WHERE `id` = ?", self.nameTxtField.text, self.relationshipTxtField.text, self.phoneTxtField.text, self.emailTxtField.text, self.remarkTxtView.text, self.photoPath, self.editContactDict[@"id"]]];
        }
        
        if (query) {
            
            if (self.contactUpdateBlock != nil) {
                self.contactUpdateBlock(true);
            }
            
            [self.navigationController popViewControllerAnimated:YES];
        }
        
        [db close];
    }
}

#pragma mark - UIImagePickerController delegate

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    self.isPhotoTook = true;
    
    NSString *fileName = [NSString stringWithFormat:@"contact_%0.f.jpg", [[NSDate date] timeIntervalSince1970]];
    
    UIImage *choseImage = info[UIImagePickerControllerOriginalImage];
    
    NSString *documentsPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    NSString *folderPath = [documentsPath stringByAppendingPathComponent:@"Media/Contact"];
    
    if (![[NSFileManager defaultManager] fileExistsAtPath:folderPath]) {
        [[NSFileManager defaultManager] createDirectoryAtPath:folderPath withIntermediateDirectories:YES attributes:nil error:nil];
    }
    NSString *filePath = [folderPath stringByAppendingPathComponent:fileName];
    NSData *imageData = UIImagePNGRepresentation(choseImage);
    
    if ([imageData writeToFile:filePath atomically:YES]) {
        self.photoPath = fileName;
    }
    
    [self.profileImgView setImage:choseImage];
    
    [picker dismissViewControllerAnimated:YES completion:nil];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [picker dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - UITextField delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField {
    self.activeTextField = textField;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    self.activeTextField = nil;
}

- (void)textViewDidBeginEditing:(UITextView *)textView {
    self.activeTextView = textView;
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    self.activeTextView = nil;
}

#pragma mark - Keyboard handling

- (void)keyboardDidShow:(NSNotification *)notification {
    NSDictionary *userInfo = [notification userInfo];
    CGFloat keyboardHeight = [[userInfo objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size.height;
    
    if (self.activeTextField) {
        if (self.activeTextField.frame.origin.y + self.activeTextField.frame.size.height >= self.view.frame.size.height - keyboardHeight) {
            [UIView animateWithDuration:0.3 animations:^{
                [self.view setFrame:CGRectMake(self.view.frame.origin.x,
                                               self.view.frame.size.height - keyboardHeight - self.activeTextField.frame.origin.y - self.activeTextField.frame.size.height - 10,
                                               self.view.frame.size.width,
                                               self.view.frame.size.height)];
            }];
        }
    }
    else {
        if (self.activeTextView.frame.origin.y + self.activeTextView.frame.size.height >= self.view.frame.size.height - keyboardHeight) {
            [UIView animateWithDuration:0.3 animations:^{
                [self.view setFrame:CGRectMake(self.view.frame.origin.x,
                                               self.view.frame.size.height - keyboardHeight - self.activeTextView.frame.origin.y - self.activeTextView.frame.size.height - 10,
                                               self.view.frame.size.width,
                                               self.view.frame.size.height)];
            }];
        }
    }
}

- (void)keyboardWillHide:(NSNotification *)notification {
    [UIView animateWithDuration:0.3 animations:^{
        [self.view setFrame:CGRectMake(self.view.frame.origin.x, 64, self.view.frame.size.width, self.view.frame.size.height)];
    }];
}

#pragma mark - Keyboard handling

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    UITouch *touch = [[event allTouches] anyObject];
    
    if ([self.nameTxtField isFirstResponder] && [touch view] != self.nameTxtField) {
        [self.nameTxtField resignFirstResponder];
    }
    else if ([self.relationshipTxtField isFirstResponder] && [touch view] != self.relationshipTxtField) {
        [self.relationshipTxtField resignFirstResponder];
    }
    else if ([self.phoneTxtField isFirstResponder] && [touch view] != self.phoneTxtField) {
        [self.phoneTxtField resignFirstResponder];
    }
    else if ([self.emailTxtField isFirstResponder] && [touch view] != self.emailTxtField) {
        [self.emailTxtField resignFirstResponder];
    }
    else if ([self.remarkTxtView isFirstResponder] && [touch view] != self.remarkTxtView) {
        [self.remarkTxtView resignFirstResponder];
    }
    
    [super touchesBegan:touches withEvent:event];
}

#pragma mark - UITapGestureRecognizer handling

- (void)demoLayerViewTap {
    if (self.currentPageNumber < 10) {
        self.currentPageNumber++;
    }
    
    [self setUpDemoData];
}

@end
