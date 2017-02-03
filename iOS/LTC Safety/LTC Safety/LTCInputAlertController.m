//
//  LTCInputAlertController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCInputAlertController.h"

@interface LTCInputAlertController () <UITextFieldDelegate>
@property (nonatomic, copy) NSString *input;
@property (nonatomic, weak) UIAlertAction *finishAction;
@end

@implementation LTCInputAlertController

+ (instancetype)alertControllerWithTitle:(NSString *)title message:(NSString *)message preferredStyle:(UIAlertControllerStyle)preferredStyle {
    
    return nil;
}

- (void)_textFieldDidChange:(UITextField *)textField {
   
}

- (void)_didFinish {
    
}

- (void)_didCancel {
    
}

@end
