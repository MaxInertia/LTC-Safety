//
//  LTCInputAlertController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCInputAlertController.h"

@interface LTCInputAlertController () <UITextFieldDelegate>

/**
 The current textfield text that the user has input.
 */
@property (nonatomic, copy) NSString *input;

/**
 The action that causes the input to be accepted when triggered. A reference to it is maintained to dispable the action when invalid input is provided.
 */
@property (nonatomic, weak) UIAlertAction *finishAction;
@end

@implementation LTCInputAlertController

/**
 Creates and returns a view controller for displaying an alert that accepts text input to the user.
 
 @pre title.length > 0
 @pre message.length > 0
 @param title The title of the alert. Use this string to get the user’s attention and communicate the reason for the alert.
 @param message Descriptive text that provides additional details about the reason for the alert.
 @param preferredStyle The style to use when presenting the alert controller. Use this parameter to configure the alert controller as an action sheet or as a modal alert.
 @return An initialized input alert controller object.
 */
+ (instancetype)alertControllerWithTitle:(NSString *)title message:(NSString *)message preferredStyle:(UIAlertControllerStyle)preferredStyle {
    
    NSAssert(title.length > 0, @"Attempted to create an alert controller with an empty title.");
    NSAssert(message.length > 0, @"Attempted to create an alert controller with an empty message.");
    NSAssert(preferredStyle == UIAlertControllerStyleActionSheet || preferredStyle == UIAlertControllerStyleAlert, @"Attempted to create an alert controller with an invalid style.");

    LTCInputAlertController *controller = [super alertControllerWithTitle:title message:message preferredStyle:preferredStyle];
    
    // Prevent retain cycle from referencing self in the block
    __weak LTCInputAlertController *weakController = controller;
    [controller addTextFieldWithConfigurationHandler:^(UITextField *textField){
        [textField addTarget:weakController action:@selector(_textFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
    }];
    
    // Add OK button for accepting input
    controller.finishAction = [UIAlertAction actionWithTitle:NSLocalizedString(@"OK", nil) style:UIAlertActionStyleDefault handler:^(UIAlertAction *action){
        [controller _didFinish];
    }];
    controller.finishAction.enabled = NO;
    [controller addAction:controller.finishAction];
    
    // Add Cancel button for canceling input
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:NSLocalizedString(@"CANCEL", nil) style:UIAlertActionStyleCancel handler:^(UIAlertAction *action){
        [controller _didCancel];
    }];
    [controller addAction:cancelAction];
    
    NSAssert([controller isKindOfClass:[LTCInputAlertController class]], @"Initialized with unexpected class type: %@", controller.class);
    NSAssert1(controller != nil, @"Failed to initialize %@", controller.class);
    
    return controller;
}

/**
 The callback whenever the textfield contents is modified by the user. Validation is performed to disable the finish action if the text input is empty.

 @param textField The textfield that the user is inputting text into.
 */
- (void)_textFieldDidChange:(UITextField *)textField {
    
    self.input = textField.text;
    self.finishAction.enabled = self.input.length > 0;
    
    NSAssert(self.input != nil, @"Input was nil after being updated by the textfield.");
}


/**
 The callback when the user taps OK causing the text input to be sent back to the delegate.
 
 @pre self.input.length > 0
 @pre The alert controller must have a non-nil delegate to notify
 */
- (void)_didFinish {
    
    NSAssert(self.input.length > 0, @"Alert controller finished with empty input.");
    NSAssert(self.delegate != nil, @"Alert controller finished with a nil delegate.");

    [self.delegate alertController:self didFinishWithInput:self.input];
}

/**
 The callback when the user traps Cancel causing the input alert controller to notify the delegate of its dismissal.
 
 @pre The alert controller must have a non-nil delegate to notify
 */
- (void)_didCancel {
    
    NSAssert(self.delegate != nil, @"Alert controller cancelled with a nil delegate.");
    
    if ([self.delegate respondsToSelector:@selector(alertControllerDidCancel:)]) {
        [self.delegate alertControllerDidCancel:self];
    }
}

@end
