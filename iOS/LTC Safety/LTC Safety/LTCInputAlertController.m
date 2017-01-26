//
//  LTCInputAlertController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCInputAlertController.h"

@interface LTCInputAlertController () <UITextFieldDelegate>
@property (nonatomic, copy) NSString *input;
@property (nonatomic, weak) UIAlertAction *finishAction;
@end

@implementation LTCInputAlertController

+ (instancetype)alertControllerWithTitle:(NSString *)title message:(NSString *)message preferredStyle:(UIAlertControllerStyle)preferredStyle {
    LTCInputAlertController *controller = [super alertControllerWithTitle:title message:message preferredStyle:preferredStyle];
    
    // Prevent retain cycle from referencing self in the block
    __weak LTCInputAlertController *weakController = controller;
    [controller addTextFieldWithConfigurationHandler:^(UITextField *textField){
        [textField addTarget:weakController action:@selector(_textFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
    }];
    
    controller.finishAction = [UIAlertAction actionWithTitle:NSLocalizedString(@"OK", nil) style:UIAlertActionStyleDefault handler:^(UIAlertAction *action){
        [controller _didFinish];
    }];
    controller.finishAction.enabled = NO;
    [controller addAction:controller.finishAction];
    
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:NSLocalizedString(@"CANCEL", nil) style:UIAlertActionStyleCancel handler:^(UIAlertAction *action){
        [controller _didCancel];
    }];
    [controller addAction:cancelAction];
    
    return controller;
}

- (void)_textFieldDidChange:(UITextField *)textField {
    self.input = textField.text;
    self.finishAction.enabled = self.input.length > 0;
}

- (void)_didFinish {
    [self.delegate alertController:self didFinishWithInput:self.input];
}

- (void)_didCancel {
    if ([self.delegate respondsToSelector:@selector(alertControllerDidCancel:)]) {
        [self.delegate alertControllerDidCancel:self];
    }
}

@end
