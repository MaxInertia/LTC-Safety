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
    
    NSAssert(title != nil, @"Attempted to create an alert controller with a nil title.");
    NSAssert(message != nil, @"Attempted to create an alert controller with a nil message.");
    NSAssert(preferredStyle == UIAlertControllerStyleActionSheet || preferredStyle == UIAlertControllerStyleAlert, @"Attempted to create an alert controller with an invalid style.");

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
    
    NSAssert([controller isKindOfClass:[LTCInputAlertController class]], @"Initialized with unexpected class type: %@", controller.class);
    NSAssert1(controller != nil, @"Failed to initialize %@", controller.class);
    
    return controller;
}

- (void)_textFieldDidChange:(UITextField *)textField {
    
    self.input = textField.text;
    self.finishAction.enabled = self.input.length > 0;
    
    NSAssert(self.input != nil, @"Input was nil after being updated by the textfield.");
}

- (void)_didFinish {
    
    NSAssert(self.input.length > 0, @"Alert controller finished with empty input.");
    NSAssert(self.delegate != nil, @"Alert controller finished with a nil delegate.");

    [self.delegate alertController:self didFinishWithInput:self.input];
}

- (void)_didCancel {
    
    NSAssert(self.delegate != nil, @"Alert controller cancelled with a nil delegate.");
    
    if ([self.delegate respondsToSelector:@selector(alertControllerDidCancel:)]) {
        [self.delegate alertControllerDidCancel:self];
    }
}

@end
